package coneforest.psi;

/**
 *	A type of basic Ψ object.
 */
public interface PsiObject
	extends
		PsiConvertableToName,
		PsiConvertableToString
{
	/**
	 *	Returns a string representation of a type name.
	 *
	 *	@return a type name.
	 */
	public String getTypeName();

	public PsiCommand psiType();

	/**
	 *	Execute this object in the context of an interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void execute(Interpreter interpreter);

	/**
	 *	Invoke this object in the context of an interpreter. Pushes this
	 *	object into interpreter’s operand stack.
	 *
	 *	@param interpreter an interpreter.
	 */
	public void invoke(Interpreter interpreter);

	public PsiBoolean psiEq(final PsiObject obj);

	public PsiBoolean psiNe(final PsiObject obj);

	/**
	 *	Returns a clone of this object.
	 *
	 *	@return a clone.
	 */
	public PsiObject psiClone();

	/**
	 *	Returns a Ψ string representing this object.
	 *
	 *	@return a Ψ string representing this object.
	 */

	public PsiBoolean psiIsA(PsiStringlike stringlike);
}
