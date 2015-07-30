package coneforest.psi;

/**
 *	A type of Ψ objects that can be closed.
 */
public interface PsiCloseable
	extends PsiObject
{
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
