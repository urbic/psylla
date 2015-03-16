package coneforest.psi;

/**
 *	A type of Ψ object that is an operand of arithmetic operation. This
 *	interface declares methods for addition, subtraction, negation,
 *	multiplication and division.
 *
 *	@param <T> a type of the second operand at binary operation.
 */
public interface PsiArithmetic<T extends PsiArithmetic>
	extends PsiObject
{
	/**
	 *	Returns a result of arithmetic negation of this object.
	 *
	 *	@return a negation.
	 */
	public T psiNeg();

	public PsiNumeric psiAbs();

	public T psiSignum();

	/**
	 *	Returns a result of arithmetic addition of given object to this object.
	 *
	 *	@param arithmetic given object.
	 *	@return a sum.
	 */
	public T psiAdd(T arithmetic);

	/**
	 *	Returns a result of arithmetic subtraction of given object from this
	 *	object.
	 *
	 *	@param arithmetic given object.
	 *	@return a difference.
	 */
	public T psiSub(T arithmetic);

	/**
	 *	Returns a result of arithmetic multiplication of given object by this object.
	 *
	 *	@param arithmetic given object.
	 *	@return a product.
	 */
	public T psiMul(T arithmetic);

	/**
	 *	Returns a result of arithmetic division of this object by given object.
	 *
	 *	@param arithmetic given object.
	 *	@return a fraction.
	 */
	public T psiDiv(T arithmetic);
}
