package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Stack;
import java.util.StringJoiner;

@Type("error")
@ErrorType("error")
abstract public class PsyErrorException
	extends	Exception
	implements PsyExecutable
{

	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyErrorException()
	{
		super();
	}

	/**
	*	Constructs a new {@code exception} object with the specified detail message.
	*
	*	@param message the detail message.
	*/
	public PsyErrorException(final String message)
	{
		super(message);
	}

	@Override
	public void invoke(final PsyContext oContext)
	{
		try
		{
			((((Interpreter)oContext).errorDict()).get(getName())).invoke(oContext);
			return;
		}
		catch(final PsyErrorException e)
		{
			// NOP
		}

		oContext.setStopped(true);
		if(oContext.currentStopLevel()>-1)
		{
			oContext.executionStack().setSize(oContext.currentStopLevel());
			return;
		}

		System.err.println(Messages.format("handleErrorMessage",
				'/'+getName(),
				getEmitter().toSyntaxString()));

		System.err.print(Messages.getString("handleErrorMessageOStack"));
		{
			final var sj=new StringJoiner(" ", "\n\t", "");
			sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
			ostack.forEach(o->sj.add(o.toSyntaxString()));
			System.err.println(sj.toString());
		}

		System.err.print(Messages.getString("handleErrorMessageEStack"));
		{
			final var sj=new StringJoiner(" ", "\n\t", "");
			sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
			estack.forEach(o->sj.add(o.toSyntaxString()));
			System.err.println(sj.toString());
		}
	}

	public String getName()
	{
		var agenda=new Stack<Class<?>>();
		agenda.push(getClass());

		while(agenda.size()>0)
		{
			var clazz=agenda.pop();
			if(clazz.isAnnotationPresent(ErrorType.class))
				return (clazz.getAnnotation(ErrorType.class)).value();
			agenda.push(clazz.getSuperclass());
			for(final var iface: clazz.getInterfaces())
				agenda.push(iface);
		}
		return null;
	};

	public void setEmitter(final PsyObject oEmitter)
	{
		this.emitter=oEmitter;
	}

	public PsyObject getEmitter()
	{
		return emitter;
	}

	public void setStacks(
			final OperandStack ostack,
			final ExecutionStack estack,
			final DictStack dstack)
	{
		this.ostack=(OperandStack)ostack.clone();
		this.estack=(ExecutionStack)estack.clone();
		this.dstack=(DictStack)dstack.clone();
	}

	private PsyObject emitter;
	private OperandStack ostack;
	private ExecutionStack estack;
	private DictStack dstack;
}
