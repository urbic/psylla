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
	{
		interpreter.getOperandStack().push(this);
	}

	/**
	 *	Query if this object is executable.
	 *
	 *	@return an executability status of this object.
	 */
	public boolean isExecutable()
	{
		return (access&ACCESS_EXECUTE)!=0;
	}

	/**
	 *	Query if this object is literal.
	 *
	 *	@return a literal status of this object.
	 */
	public boolean isLiteral()
	{
		return !isExecutable();
	}

	/**
	 *	Make this object executable.
	 */
	public void setExecutable()
	{
		access|=ACCESS_EXECUTE;
	}

	/**
	 *	Make this object literal.
	 */
	public void setLiteral()
	{
		access&=~ACCESS_EXECUTE;
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

	public PsiBoolean psiIsA(PsiStringlike stringlike)
	{
		Class<? extends PsiObject> clazz=TypeRegistry.get(stringlike.getString());
		return new PsiBoolean(clazz!=null && clazz.isInstance(this));
	}

	private static final byte
		ACCESS_NOACCESS=0,
		ACCESS_EXECUTE=1;

	private byte access=ACCESS_NOACCESS;

	//private static final String TYPE_NAME="object";
}
