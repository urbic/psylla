package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.util.Stack;

@SuppressWarnings("serial")
@Type("error")
@ErrorType("error")
public class PsyErrorException
	extends	Exception
	implements PsyExecutable
{
	/**
	*	The emitter.
	*/
	private PsyObject emitter;

	/**
	*	The operand stack.
	*/
	private OperandStack ostack;

	/**
	*	The execution stack.
	*/
	private ExecutionStack estack;

	/**
	*	The dictionary stack.
	*/
	private DictStack dstack;

	/**
	*	Constructs a new {@code exception} object with null as its detail message.
	*/
	public PsyErrorException()
	{
	}

	/**
	*	Constructs a new {@code exception} object with the specified cause.
	*
	*	@param cause the cause.
	*/
	public PsyErrorException(final Throwable cause)
	{
		super(cause);
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

		if(oContext.executionStack().checkStop() /*oContext.currentStopLevel()>-1*/)
		{
			//oContext.executionStack().setSize(oContext.currentStopLevel());
			oContext.executionStack().exitStop();
			return;
		}
		else
		{
			if(oContext.executionStack().checkLoop())
			{
				try
				{
					oContext.executionStack().exitLoop();
				}
				catch(final PsyInvalidExitException e)
				{
					// NOP
				}
			}
			oContext.stop_();
		}

		System.err.println(Messages.format("handleErrorMessage",
				'/'+getName(),
				getEmitter().toSyntaxString()));

		oContext.showStacks();
		/*
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
		*/
	}

	/**
	*	{@return the name of this {@code error}}
	*/
	public String getName()
	{
		final var agenda=new Stack<Class<?>>();
		agenda.push(getClass());

		while(agenda.size()>0)
		{
			final var clazz=agenda.pop();
			if(clazz.isAnnotationPresent(ErrorType.class))
				return (clazz.getAnnotation(ErrorType.class)).value();
			agenda.push(clazz.getSuperclass());
			for(final var iface: clazz.getInterfaces())
				agenda.push(iface);
		}
		return null;
	}

	/**
	*	Set the emitter for this {@code error}.
	*
	*	@param oEmitter the emitter.
	*/
	public void setEmitter(final PsyObject oEmitter)
	{
		this.emitter=oEmitter;
	}

	/**
	*	{@return the emitter for this {@code error}}
	*/
	public PsyObject getEmitter()
	{
		return emitter;
	}

	/**
	*	Set the cloned copies of operand, execution and dictionary stacks.
	*
	*	@param ostack the operand stack.
	*	@param estack the execution stack.
	*	@param dstack the dictionary stack.
	*/
	public void setStacks(
			final OperandStack ostack,
			final ExecutionStack estack,
			final DictStack dstack)
	{
		this.ostack=ostack.clone();
		this.estack=estack.clone();
		this.dstack=dstack.clone();
	}
}
