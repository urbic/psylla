package coneforest.psi;

/**
 *	A type of Ψ object that is an operand of arithmetic operation. This
 *	interface declares methods for addition, subtraction, negation,
 *	multiplication and division.
 *
 *	@param <T> a type of the second operand at binary operation.
 */
public interface PsiArithmetic<T extends PsiArithmetic>
	extends
		PsiAdditive<T>
{
	@Override
	default public String getTypeName()
	{
		return "arithmetic";
	}

	public T psiSignum();

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
