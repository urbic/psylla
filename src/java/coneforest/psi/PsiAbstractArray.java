package coneforest.psi;

abstract public class PsiAbstractArray<T extends PsiObject>
	implements PsiArraylike<T>
{
	@Override
	public PsiBoolean psiKnown(PsiInteger key)
	{
		long keyValue=key.longValue();
		return PsiBoolean.valueOf(keyValue>=0 && keyValue<length());
	}

	@Override
	public boolean isEmpty()
	{
		return length()==0;
	}

	@Override
	public PsiInteger psiLength()
	{
		return PsiInteger.valueOf(length());
	}

	@Override
	public PsiAbstractArray<T> psiNewEmpty()
		throws PsiException
	{
		try
		{
			return getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e)
		{
			throw new PsiException("unknownerror");
		}
	}

	@Override
	abstract public PsiAbstractArray<T> psiClone();

	@Override
	public T psiGet(PsiInteger index)
		throws PsiException
	{
		return get(index.intValue());
	}

	@Override
	public void psiPut(PsiInteger index, T obj)
		throws PsiException
	{
		put(index.intValue(), obj);
	}

	@Override
	public void psiPutInterval(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		if(indexValue<0
			||
			iterable instanceof PsiLengthy
			&& indexValue+((PsiLengthy)iterable).length()>=length())
			throw new PsiException("rangecheck");
		for(T obj: iterable)
		{
			put(indexValue++, obj);
			if(indexValue==length())
				break;
		}
	}

	@Override
	abstract public void insert(int indexValue, T obj)
		throws PsiException;

	@Override
	public void psiInsert(PsiInteger index, T obj)
		throws PsiException
	{
		insert(index.intValue(), obj);
	}

	@Override
	public void psiAppendAll(final PsiIterable<? extends T> iterable)
		throws PsiException
	{
		for(T obj: (this!=iterable? iterable: (PsiIterable<? extends T>)psiClone()))
			psiAppend(obj);
	}

	@Override
	public void psiInsertAll(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		for(T obj: (this!=iterable? iterable: (PsiIterable<? extends T>)psiClone()))
			insert(indexValue++, obj);
	}

	@Override
	public void psiPrepend(T obj)
		throws PsiException
	{
		insert(0, obj);
	}

	@Override
	public void psiPrependAll(PsiIterable<? extends T> iterable)
		throws PsiException
	{
		psiInsertAll(PsiInteger.ZERO, iterable);
	}

	@Override
	public T psiExtract(final PsiInteger index)
		throws PsiException
	{
		return extract(index.intValue());
	}

	@Override
	public PsiAbstractArray<T> psiReplicate(final PsiInteger count)
		throws PsiException
	{
		long countValue=count.longValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		if(countValue>Integer.MAX_VALUE)
			throw new PsiException("limitcheck");
		PsiAbstractArray<T> result=psiNewEmpty();
		while(countValue-->0)
			result.psiAppendAll(this);
		return result;
	}

	@Override
	public PsiAbstractArray<T> psiReverse()
		throws PsiException
	{
		PsiAbstractArray<T> result=psiClone();
		int length=result.length();
		for(int i=0; i<(int)(length/2); i++)
		{
			T obj=result.get(i);
			result.put(i, result.get(length-1-i));
			result.put(length-1-i, obj);
		}
		return result;
	}

	@Override
	public PsiAbstractArray<T> psiGetAll(PsiIterable<PsiInteger> iterable)
		throws PsiException
	{
		PsiAbstractArray<T> result=psiNewEmpty();
		for(PsiInteger integer: iterable)
			result.psiAppend(psiGet(integer));
		return result;
	}

	@Override
	public String toString()
	{
		return "["+toStringHelper(this)+"]";
	}

	@Override
	public String toStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toString());
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
