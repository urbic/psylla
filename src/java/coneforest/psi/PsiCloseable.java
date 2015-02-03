package coneforest.psi;

/**
 *	A type of Ψ objects that can be closed.
 */
public interface PsiCloseable
	extends PsiObject
{
	/**
	 *	Close this object.
	 *
	 *	@throws PsiException when error occured during closing.
	 */
	public void psiClose()
		throws PsiException;
}
