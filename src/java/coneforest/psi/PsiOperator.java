package coneforest.psi;

/**
 *	A representation of Ψ-<code class="type">operator</code> object.
 */
public abstract class PsiOperator
	implements PsiAtomic
{
	/**
	 *	@return a string <code class="constant">"operator"</code>.
	 */
	@Override
	public String getTypeName()
	{
		return "operator";
	}

	/**
	 *	Execute this object in the context of the interpreter.  Calls {@link
	 *	#invoke(Interpreter)} method.
	 */
	public void execute(Interpreter interpreter)
	{
		invoke(interpreter);
	}

	/**
	 *	Invoke this object in the context of the interpreter performing
	 *	an action associated with it.
	 */
	public void invoke(Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.clearBackup();
		try
		{
			action(interpreter);
		}
		catch(ClassCastException e)
		{
			opstack.restore();
			interpreter.handleError("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.restore();
			interpreter.handleError(e.kind(), this);
		}
	}

	abstract public void action(Interpreter interpreter)
		throws ClassCastException, PsiException;

	/**
	 *	Returns a syntatctic string representation of this operator.
	 *	A syntatctic representation has a form of
	 *	<code>"--<i>name</i>--"</code>, where <code><i>name</i></code> is a
	 *	string returned by {@link #getName()} method.
	 *
	 *	@return a syntatctic string representation of a name of this operator.
	 */
	@Override
	public String toString()
	{
		return "--"+getName()+"--";
	}

	/**
	 *	Returns a name of this operator. A name returned is an operator’s
	 *	simple class name with first character (underscore) discarded. This
	 *	method must be overriden when using another naming scheme.
	 *
	 *	@return a name.
	 */
	public String getName()
	{
		return getClass().getSimpleName().substring(1);
	}
}
