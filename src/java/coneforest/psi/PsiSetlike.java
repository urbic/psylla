package coneforest.psi;

public interface PsiSetlike<T extends PsiObject>
	extends PsiComposite<T>, PsiIterable<T>, PsiAppendable<T>
{
	/*
	@Override
	public void psiAppend(T obj)
		throws PsiException;

	@Override
	public void psiAppendAll(PsiSetlike<? extends T> setlike)
		throws PsiException;
	*/

	public void psiRemove(T obj)
		throws PsiException;

	public void psiRemoveAll(PsiIterable<? extends T> setlike)
		throws PsiException;

	public PsiBoolean psiContains(T obj);

	public PsiBoolean psiIntersects(PsiSetlike<? extends T> setlike);
}
