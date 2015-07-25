package coneforest.psi;

public interface PsiPrependable<T extends PsiObject>
	extends PsiObject
{
	public void psiPrepend(T obj)
		throws PsiException;

	public void psiPrependAll(PsiIterable<? extends T> setlike)
		throws PsiException;
}
