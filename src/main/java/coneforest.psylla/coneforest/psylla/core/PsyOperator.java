package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code operator}.
*/
@Type("operator")
public abstract class PsyOperator
	implements
		PsyAtomic,
		PsyExecutable
{
	/**
	*	Constructs a new {@code operator} with the given name.
	*
	*	@param name the name.
	*/
	protected PsyOperator(final String name)
	{
		this.name=name;
	}

	/**
	*	Execute this object in the execution context. Calls the {@link #invoke(PsyContext)} method.
	*
	*	@param oContext the execution context.
	*/
	@Override
	public void execute(final PsyContext oContext)
	{
		invoke(oContext);
	}

	/**
	*	Invoke this operator in the execution context performing an action associated with it.
	*
	*	@param oContext the execution context.
	*/
	@Override
	public void invoke(final PsyContext oContext)
	{
		final var ostack=oContext.operandStack();
		ostack.clearBackup();
		try
		{
			perform(oContext);
		}
		catch(final ClassCastException ex)
		{
			ostack.rollback();
			final var e=new PsyTypeCheckException();
			e.setEmitter(this);
			final var estack=oContext.executionStack();
			final var dstack=oContext.dictStack();
			e.setStacks(ostack, estack, dstack);
			e.invoke(oContext);
		}
		catch(final PsyErrorException e)
		{
			ostack.rollback();
			e.setEmitter(this);
			final var estack=oContext.executionStack();
			final var dstack=oContext.dictStack();
			e.setStacks(ostack, estack, dstack);
			e.invoke(oContext);
		}
	}

	abstract public void perform(final PsyContext oContext)
		throws PsyErrorException;

	/**
	*	Returns a syntactic string representation of this operator. A syntatctic representation has
	*	a form of <code>"--<i>name</i>--"</code>, where <code><i>name</i></code> is a string
	*	returned by {@link #getName()} method.
	*
	*	@return a syntactic string representation of a name of this operator.
	*/
	@Override
	public String toSyntaxString()
	{
		return "--"+name+"--";
	}

	/**
	*	Returns a name of this operator.
	*
	*	@return a name.
	*/
	public String getName()
	{
		return name;
	}

	public String getPrefix()
	{
		final var prefixOffset=getName().indexOf('@');
		return prefixOffset<0? "core": getName().substring(0, prefixOffset);
	}

	public String getSimpleName()
	{
		return getName().substring(getPrefix().length()+1);
	}

	private final String name;
}
