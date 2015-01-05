package coneforest.psi;

public interface PsiContainer<T extends PsiObject>
	extends PsiLengthy, PsiIterable<T>
{
	public PsiContainer<T> psiCloneEmpty()
		throws PsiException;
}
