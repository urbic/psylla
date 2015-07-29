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
	default public boolean isEmpty()
	{
		return length()==0;
	}

	/**
	 *	Returns a Ψ-<code class="type">integer</code> representing the length
	 *	of this object.
	 *
	 *	@return a length.
	 */
	default public PsiInteger psiLength()
	{
		return PsiInteger.valueOf(length());
	}

	/**
	 *	Returns a Ψ-<code class="type">boolean</code> indicating if this object
	 *	is empty (has zero length).
	 *
	 *	@return a result.
	 */
	default public PsiBoolean psiIsEmpty()
	{
		return PsiBoolean.valueOf(isEmpty());
	}

	//public String toSyntaxStringHelper(PsiLengthy lengthy);
}
