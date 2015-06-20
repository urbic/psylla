package coneforest.psi;

public interface PsiContainer<T extends PsiObject>
	extends
		PsiIterable<T>,
		PsiLengthy
{
	public PsiContainer<T> psiNewEmpty()
		throws PsiException;
}
