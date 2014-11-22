package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">operator</code> object.
 */
public abstract class PsiOperator extends PsiObject
{
	/**
	 *	Creates a new Ψ operator object with executable flag set.
	 */
	public PsiOperator()
	{
		super();
		setExecutable();
	}

	/**
	 *	Returns a string representation of a type name (<code>"operator"</code>).
	 *
	 *	@return a string representation of a type name.
	 */
	@Override
	public String getTypeName()
	{
		return "operator";
	}

	/**
	 *	Execute this object in the context of the interpreter. For literal
	 *	operators pushes this object on interpreter’s operand stack. For
	 *	executable operators calls {@link #invoke(Interpreter)} method.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
			invoke(interpreter);
		else
			super.execute(interpreter);
	}

	/**
	 *	Invoke this object in the context of the interpreter performing an
	 *	action associated with it.
	 *
	 *	@param interpreter an interpreter.
	 */
	public abstract void invoke(Interpreter interpreter);

	/**
	 *	Returns a syntatctic string representation of a name of this operator.
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
	 *	@return a name of this operator.
	 */
	public String getName()
	{
		return getClass().getSimpleName().substring(1);
	}
}
