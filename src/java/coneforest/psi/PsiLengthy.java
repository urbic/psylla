package coneforest.psi;

/**
 *	A type of Ψ objects that have “length” in some sense.
 */
public interface PsiLengthy
	extends PsiObject
{
	/**
	 *	Returns a length of this object.
	 *
	 *	@return a length.
	 */
	public int length();

	/**
	 *	Returns a boolean indicating if this object is empty (has zero length).
	 *
	 *	@return a result.
	 */
	public boolean isEmpty();

	/**
	 *	Returns a Ψ-<code class="type">integer</code> representing the length
	 *	of this object.
	 *
	 *	@return a length.
	 */
	public PsiInteger psiLength();

	/**
	 *	Returns a Ψ-<code class="type">boolean</code> indicating if this object
	 *	is empty (has zero length).
	 *
	 *	@return a result.
	 */
	public PsiBoolean psiIsEmpty();

	public String toStringHelper(PsiLengthy lengthy);
}
