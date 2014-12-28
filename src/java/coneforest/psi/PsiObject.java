package coneforest.psi;

/**
 *	A representation of basic Ψ <code class="type">object</code>.
 */
public interface PsiObject
	extends PsiConvertableToString
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return a type name.
	 */
	public String getTypeName();

	/**
	 *	Execute this object in the context of the interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void execute(Interpreter interpreter);

	/**
	 *	Invoke this object in the context of the interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void invoke(Interpreter interpreter);

	/**
	 *	Query if this object is executable.
	 *
	 *	@return an executability status of this object.
	 */
	public boolean isExecutable();

	/**
	 *	Query if this object is literal.
	 *
	 *	@return a literal status of this object.
	 */
	public boolean isLiteral();

	/**
	 *	Make this object executable.
	 */
	public void setExecutable();

	/**
	 *	Make this object literal.
	 */
	public void setLiteral();

	public PsiBoolean psiEq(final PsiObject obj);

	public PsiBoolean psiNe(final PsiObject obj);

	/**
	 *	Returns this object.
	 *
	 *	@return this object.
	 */
	public PsiObject psiClone();

	/**
	 *	Returns a Ψ string representing this object.
	 *
	 *	@return a Ψ string representing this object.
	 */
	@Override
	public PsiString psiToString();

	@Override
	public String toString();

	public PsiBoolean psiIsA(PsiStringlike stringlike);

	//private static final byte
	//	ACCESS_NOACCESS=0,
	//	ACCESS_EXECUTE=1;

	//private byte access=ACCESS_NOACCESS;

	//private static final String TYPE_NAME="object";
}
