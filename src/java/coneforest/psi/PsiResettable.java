package coneforest.psi;

/**
 *	A type of Ψ objects that can be reset.
 */
public interface PsiResettable
	extends PsiObject
{
	/**
	 *	Reset this object.
	 *
	 *	@throws PsiException when error occured during reset.
	 */
	public void psiReset()
		throws PsiException;
}
