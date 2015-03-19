package coneforest.psi;

/**
 *	An interface bringing total ordering to implementing Ψ type.
 *
 *	@param T A type of object compared against this object.
 */
public interface PsiScalar<T extends PsiScalar>
	extends PsiObject
{
	/**
	 *	“Less” comparison.
	 *
	 *	@param scalar An object with which this object is compared.
	 *	@return A Ψ boolean value indicating if this object is less than given
	 *	object.
	 */
	public PsiBoolean psiLt(T scalar);

	/**
	 *	“Less or equal” comparison.
	 *
	 *	@param scalar An object with which this object is compared.
	 *	@return A Ψ boolean value indicating if this object is less than or
	 *	equal to given object.
	 */
	public PsiBoolean psiLe(T scalar);

	/**
	 *	“Greater” comparison.
	 *
	 *	@param scalar An object with which this object is compared.
	 *	@return A Ψ boolean value indicating if this object is greater than
	 *	given object.
	 */
	public PsiBoolean psiGt(T scalar);

	/**
	 *	“Greater or equal” comparison.
	 *
	 *	@param scalar An object with which this object is compared.
	 *	@return A Ψ boolean value indicating if this object is greater than or
	 *	equal to given object.
	 */
	public PsiBoolean psiGe(T scalar);

	/**
	 *	Compares this object against given object and returns a Ψ-<code
	 *	class="type">integer</code> indicating the result of the comparison.
	 *	Returns negative value if this object is less than given one, zero if
	 *	this object is equal to given one, and positive value if this object is
	 *	greater than given one.
	 *
	 *	@param scalar An object with which this object is compared.
	 *	@return A result of the comparison.
	 */
	public PsiInteger psiCmp(T scalar);
}
