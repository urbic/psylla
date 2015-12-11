package coneforest.psi;

/**
 *	A type of Ψ object that is an operand of arithmetic operation. This
 *	interface declares methods for addition, subtraction and negation,
 *
 *	@param <T> a type of the second operand at binary operation.
 */
public interface PsiAdditive<T extends PsiAdditive>
	extends PsiObject
{
	/**
	 *	@return a string {@code "additive"}.
	 */
	@Override
	default public String getTypeName()
	{
		return "additive";
	}

	/**
	 *	Returns a result of arithmetic negation of this object.
	 *
	 *	@return a negation.
	 */
	public T psiNeg();

	/**
	 *	Returns a result of arithmetic addition of given object to this object.
	 *
	 *	@param additive given object.
	 *	@return a sum.
	 */
	public T psiAdd(T additive);

	/**
	 *	Returns a result of arithmetic subtraction of given object from this
	 *	object.
	 *
	 *	@param additive given object.
	 *	@return a difference.
	 */
	public T psiSub(T additive);
}
