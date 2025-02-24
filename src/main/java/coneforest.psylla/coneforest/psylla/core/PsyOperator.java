package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code operator}.
*/
@Type("operator")
public abstract class PsyOperator
	implements PsyExecutable, PsyValue
{
	/**
	*	The name of this operator.
	*/
	private final String name;

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
		catch(final OutOfMemoryError ex)
		{
			ostack.rollback();
			final var e=new PsyLimitCheckException();
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

	/**
	*	Perform an action associated with this operator in the given execution context.
	*
	*	@param oContext the execution context.
	*	@throws PsyErrorException when an error occurs.
	*/
	public abstract void perform(final PsyContext oContext)
		throws PsyErrorException;

	/**
	*	{@return a syntactic string representation of this operator} A syntatctic representation has
	*		a form of <code>"--<i>name</i>--"</code>, where <code><i>name</i></code> is a string
	*		returned by {@link #getName()} method.
	*/
	@Override
	public String toSyntaxString()
	{
		return "--"+name+"--";
	}

	/**
	*	{@return the name of this operator}
	*/
	public String getName()
	{
		return name;
	}

	/**
	*	{@return the namespace prefix of this operator}
	*/
	public String getPrefix()
	{
		final var prefixOffset=getName().indexOf('@');
		return prefixOffset<0? "core": getName().substring(0, prefixOffset);
	}

	/**
	*	{@return the simple name of this operator}
	*/
	public String getSimpleName()
	{
		return getName().substring(getPrefix().length()+1);
	}
}
