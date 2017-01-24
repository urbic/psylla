package coneforest.psi.core;

/**
*	A representation of Ψ-{@code arithmetic}, a type of object that is an
*	operand of arithmetic operation. This interface declares methods for
*	multiplication, division.
*
*	@param <T> a type of the second operand at binary operation.
*/
public interface PsiArithmetic<T extends PsiArithmetic>
	extends
		PsiAdditive<T>
{
	/**
	*	@return a string {@code "arithmetic"}.
	*/
	@Override
	default public String typeName()
	{
		return "arithmetic";
	}

	/**
	*	Returns a result of arithmetic multiplication of given object by this object.
	*
	*	@param oArithmetic a given object.
	*	@return a product.
	*/
	public T psiMul(final T oArithmetic);

	/**
	*	Returns a result of arithmetic division of this object by given object.
	*
	*	@param oArithmetic a given object.
	*	@return a fraction.
	*/
	public T psiDiv(final T oArithmetic);

	/**
	*	Returns a Ψ-{@code boolean} indicating whether this object represents a
	*	zero value.
	*
	*	@return {@link PsiBoolean#TRUE} if this object represents a zero value,
	*	and {@link PsiBoolean#FALSE} otherwise.
	*/
	public PsiBoolean psiIsZero();

	/**
	*	Returns a Ψ-{@code boolean} indicating whether this object represents a
	*	non-zero value.
	*
	*	@return {@link PsiBoolean#TRUE} if this object represents a non-zero value,
	*	and {@link PsiBoolean#FALSE} otherwise.
	*/
	default public PsiBoolean psiNotZero()
	{
		return psiIsZero().psiNot();
	}
}
