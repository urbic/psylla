package coneforest.psi;

abstract public class PsiAbstractArray<T extends PsiObject>
	extends PsiObject
	implements PsiArraylike<T>
{
	@Override
	public String getTypeName()
	{
		return "array";
	}

	@Override
	public boolean isEmpty()
	{
		return length()==0;
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(length());
	}

	@Override
	public T psiGet(PsiInteger index)
		throws PsiException
	{
		return psiGet(index.getValue().intValue());
	}

	@Override
	public void psiPut(PsiInteger index, T obj)
		throws PsiException
	{
		psiPut(index.getValue().intValue(), obj);
	}

	@Override
	public void psiInsert(PsiInteger index, T obj)
		throws PsiException
	{
		int indexValue=index.getValue().intValue();
		psiAppend(psiGet(length()));
		for(int i=length(); i>indexValue; i--)
			psiPut(i, psiGet(i-1));
		psiPut(indexValue, obj);
	}

	@Override
	public void psiAppendAll(PsiIterable<? extends T> iterable)
		throws PsiException
	{
		for(T obj: iterable)
			psiAppend(obj);
	}

	@Override
	public void psiInsertAll(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException
	{
		for(T obj: iterable)
			psiInsert(index, obj);
	}
}
