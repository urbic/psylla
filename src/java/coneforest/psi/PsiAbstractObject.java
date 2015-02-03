package coneforest.psi;

/**
 *	A representation of basic Ψ <code class="type">object</code>.
 */
abstract public class PsiAbstractObject
	implements PsiObject
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return a type name.
	 */
	abstract public String getTypeName();

	/**
	 *	Execute this object in the context of the interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void execute(Interpreter interpreter)
		//throws PsiException
	{
		interpreter.getOperandStack().push(this);
	}

	/**
	 *	Invoke this object in the context of the interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void invoke(Interpreter interpreter)
		//throws PsiException
	{
		interpreter.getOperandStack().push(this);
	}

	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(this==obj);
	}

	public PsiBoolean psiNe(final PsiObject obj)
	{
		return psiEq(obj).psiNot();
	}

	/**
	 *	Returns this object.
	 *
	 *	@return this object.
	 */
	public PsiObject psiClone()
	{
		return this;
	}

	/**
	 *	Returns a Ψ string representing this object.
	 *
	 *	@return a Ψ string representing this object.
	 */
	public PsiString psiToString()
	{
		return new PsiString(toString());
	}

	@Override
	public String toString()
	{
		return "-"+getTypeName()+"-";
	}

	@Override
	public PsiBoolean psiIsA(PsiStringlike stringlike)
	{
		Class<? extends PsiObject> clazz=TypeRegistry.get(stringlike.getString());
		return new PsiBoolean(clazz!=null && clazz.isInstance(this));
	}

	//private static final String TYPE_NAME="object";
}
