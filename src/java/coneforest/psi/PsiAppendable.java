package coneforest.psi;

public interface PsiAppendable<T extends PsiObject>
{
	public void psiAppend(T obj)
		throws PsiException;

	public void psiAppendAll(PsiIterable<? extends T> setlike)
		throws PsiException;
}
