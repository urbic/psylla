package coneforest.psi;

public interface PsiArraylike<T extends PsiObject>
	extends
		PsiLengthy<T>,
		PsiIndexed<PsiInteger, T>,
		PsiIterable<T>,
		PsiAppendable<T>,
		PsiClearable
{
	public T psiGet(int indexValue)
		throws PsiException;

	public PsiArraylike<T> psiGetInterval(PsiInteger index, PsiInteger count)
		throws PsiException;

	public void psiPut(int indexValue, T obj)
		throws PsiException;

	public void psiPutInterval(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException;

	public void psiInsert(int indexValue, T obj)
		throws PsiException;

	public void psiInsert(PsiInteger index, T obj)
		throws PsiException;

	public void psiInsertAll(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException;

	public void psiReverse()
		throws PsiException;
}
