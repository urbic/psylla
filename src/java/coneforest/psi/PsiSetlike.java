package coneforest.psi;

public interface PsiSetlike<T extends PsiObject>
	extends PsiComposite<T>, PsiIterable<T>, PsiAppendable<T>
{
	public void psiRemove(T obj)
		throws PsiException;

	public void psiRemoveAll(PsiIterable<? extends T> iterable)
		throws PsiException;

	public void psiRetainAll(PsiIterable<? extends T> iterable)
		throws PsiException;

	public void psiRetainAll(PsiSetlike<? extends T> setlike)
		throws PsiException;

	public PsiBoolean psiContains(T obj);

	public PsiBoolean psiIntersects(PsiSetlike<? extends T> setlike);
}
