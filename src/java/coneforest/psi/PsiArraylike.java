package coneforest.psi;

public interface PsiArraylike<T extends PsiObject>
	extends
		PsiContainer<T>,
		PsiIndexed<PsiInteger, T>,
		PsiAppendable<T>,
		PsiPrependable<T>,
		PsiClearable
{
	public T get(int indexValue)
		throws PsiException;

	public PsiArraylike<T> psiGetInterval(PsiInteger index, PsiInteger count)
		throws PsiException;

	public void put(int indexValue, T obj)
		throws PsiException;

	public void psiPutInterval(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException;

	public void insert(int indexValue, T obj)
		throws PsiException;

	public void psiInsert(PsiInteger index, T obj)
		throws PsiException;

	public void psiInsertAll(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException;

	public PsiArraylike psiReverse()
		throws PsiException;

	public void psiSetLength(PsiInteger length)
		throws PsiException;

	public T delete(int indexValue)
		throws PsiException;

	public PsiArraylike<T> psiDeleteInterval(PsiInteger index, PsiInteger count)
		throws PsiException;

	@Override
	public PsiArraylike<T> psiSlice(PsiIterable<PsiInteger> indices)
		throws PsiException;
}
