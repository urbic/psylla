package coneforest.psylla.core.errors;

import coneforest.psylla.DictStack;
import coneforest.psylla.ErrorType;
import coneforest.psylla.ExecutionStack;
import coneforest.psylla.Interpreter;
import coneforest.psylla.Messages;
import coneforest.psylla.OperandStack;
import coneforest.psylla.core.types.PsyContext;
import coneforest.psylla.core.types.PsyExecutable;
import coneforest.psylla.core.types.PsyObject;
import java.util.StringJoiner;

@ErrorType("error")
abstract public class PsyError
	extends	Exception
	implements PsyExecutable
{
	public void invoke(final PsyContext oContext)
	{
		try
		{
			((((Interpreter)oContext).errorDict()).get(getName())).invoke(oContext);
			return;
		}
		catch(final PsyError e)
		{
			// NOP
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

		oContext.quit();
	}

	public String getName()
	{
		var agenda=new java.util.Stack<Class<?>>();
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
