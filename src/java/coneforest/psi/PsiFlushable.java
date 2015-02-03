package coneforest.psi;

/**
 *	A type of Ψ objects that can be flushed.
 */
public interface PsiFlushable
	extends PsiObject
{
	/**
	 *	Flush this object.
	 *
	 *	@throws PsiException when error occured during flush.
	 */
	public void psiFlush()
		throws PsiException;
}
