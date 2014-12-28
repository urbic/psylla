package coneforest.psi;

public interface PsiLogical<T extends PsiLogical>
	extends PsiObject
{
	public T psiNot();

	public T psiOr(final T obj);

	public T psiAnd(final T obj);

	public T psiXor(final T obj);
}
