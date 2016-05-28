package coneforest.psi.core;

/**
*	A representation of a Ψ-{@code scalar}, a type bringing total ordering to
*	implementing Ψ type. This interface declares methods for comparison.
*
*	@param <T> a type of the second operand at binary comparison operation.
*/
public interface PsiScalar<T extends PsiScalar>
	extends PsiObject
{
	/**
	*	@return a string {@code "scalar"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "scalar";
	}

	/**
	*	Returns a Ψ-{@code boolean} object representing the result of “less”
	*	comparison of this object and a given object.
	*
	*	@param oScalar An object with which this object is compared.
	*	@return A Ψ boolean value indicating if this object is less than given
	*	object.
	*/
	public PsiBoolean psiLt(final T oScalar);

	/**
	*	Returns a Ψ-{@code boolean} object representing the result of “less or
	*	equal” comparison of this object and a given object.
	*
	*	@param oScalar An object with which this object is compared.
	*	@return A Ψ boolean value indicating if this object is less than or
	*	equal to given object.
	*/
	public PsiBoolean psiLe(final T oScalar);

	/**
	*	Returns a Ψ-{@code boolean} object representing the result of “greater”
	*	comparison of this object and a given object.
	*
	*	@param oScalar an object with which this object is compared.
	*	@return A Ψ-{@code boolean} result of comparison.
	*/
	public PsiBoolean psiGt(final T oScalar);

	/**
	*	Returns a Ψ-{@code boolean} object representing the result of “greater
	*	or equal” comparison of this object and a given object.
	*
	*	@param oScalar An object with which this object is compared.
	*	@return A Ψ-{@code boolean} result of comparison.
	*/
	public PsiBoolean psiGe(final T oScalar);

	/**
	*	Compares this object against given object and returns a Ψ-{@code
	*	integer} indicating the result of the comparison. Returns negative
	*	value if this object is less than given one, zero if this object is
	*	equal to given one, and positive value if this object is greater than
	*	given one.
	*
	*	@param oScalar An object with which this object is compared.
	*	@return A result of the comparison.
	*/
	public PsiInteger psiCmp(final T oScalar);

	default public PsiScalar psiMin(final T oScalar)
	{
		return psiLt(oScalar).booleanValue()? this: oScalar;
	}

	default public PsiScalar psiMax(final T oScalar)
	{
		return psiGt(oScalar).booleanValue()? this: oScalar;
	}
}
