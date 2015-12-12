package coneforest.psi;

/**
*	A representation of Ψ-{@code closeable}, a type of objects that can be
*	closed in some sense.
*/
public interface PsiCloseable
	extends PsiObject
{
	/**
	*	@return a string {@code "closeable"}.
	*/
	@Override
	default public String getTypeName()
	{
		return "closeable";
	}

	/**
	 *	Close this object.
	 *
	 *	@throws PsiException when error occured during closing.
	 */
	public void psiClose()
		throws PsiException;
}
