package coneforest.psi;

public interface PsiSetlike<T extends PsiObject>
	extends
		PsiContainer<T>,
		PsiAppendable<T>,
		PsiClearable
{
	public void psiRemove(T obj);

	public void psiRemoveAll(PsiIterable<? extends T> iterable);

	public void psiRetainAll(PsiIterable<? extends T> iterable)
		throws PsiException;

	public void psiRetainAll(PsiSetlike<? extends T> setlike)
		throws PsiException;

	public PsiBoolean psiContains(T obj);

	public PsiBoolean psiIntersects(PsiSetlike<? extends T> setlike);
}
