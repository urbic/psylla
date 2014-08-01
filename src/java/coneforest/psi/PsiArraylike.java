package coneforest.psi;

public interface PsiArraylike<T extends PsiObject>
	extends
		PsiIndexed<PsiInteger, T>,
		PsiComposite<T>,
		PsiIterable<T>,
		PsiAppendable<T>
{
	public T psiGet(int indexValue)
		throws PsiException;

	@Override
	public T psiGet(PsiInteger index)
		throws PsiException;

	public void psiPut(int indexValue, T obj)
		throws PsiException;

	@Override
	public void psiPut(PsiInteger index, T obj)
		throws PsiException;

	public void psiInsert(int indexValue, T obj)
		throws PsiException;

	public void psiInsert(PsiInteger index, T obj)
		throws PsiException;

	public void psiInsertAll(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException;
}
