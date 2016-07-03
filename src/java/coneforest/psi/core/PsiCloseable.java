package coneforest.psi.core;

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
	default public String typeName()
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
