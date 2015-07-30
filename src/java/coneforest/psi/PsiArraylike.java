package coneforest.psi;

public interface PsiArraylike<T extends PsiObject>
	extends
		PsiContainer<T>,
		PsiIndexed<PsiInteger, T>,
		PsiAppendable<T>,
		PsiPrependable<T>,
		PsiClearable
{
	@Override
	default public String getTypeName()
	{
		return "arraylike";
	}

	@Override
	public PsiArraylike<T> psiClone();

	default public PsiArraylike<T> psiReverse()
		throws PsiException
	{
		PsiArraylike<T> result=psiClone();
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
	default public PsiBoolean psiKnown(PsiInteger key)
	{
		long keyValue=key.longValue();
		return PsiBoolean.valueOf(keyValue>=0 && keyValue<length());
	}

	public T get(int indexValue)
		throws PsiException;

	@Override
	default public T psiGet(PsiInteger index)
		throws PsiException
	{
		return get(index.intValue());
	}

	public PsiArraylike<T> psiGetInterval(PsiInteger index, PsiInteger count)
		throws PsiException;

	public void put(int indexValue, T obj)
		throws PsiException;

	@Override
	default public void psiPut(PsiInteger index, T obj)
		throws PsiException
	{
		put(index.intValue(), obj);
	}

	public void insert(int indexValue, T obj)
		throws PsiException;

	@Override
	default public void psiAppendAll(final PsiIterable<? extends T> iterable)
		throws PsiException
	{
		for(T obj: (this!=iterable? iterable: (PsiIterable<? extends T>)psiClone()))
			psiAppend(obj);
	}

	default public void psiInsert(PsiInteger index, T obj)
		throws PsiException
	{
		insert(index.intValue(), obj);
	}

	default public void psiInsertAll(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException
	{
		int indexValue=index.intValue();
		for(T obj: (this!=iterable? iterable: (PsiIterable<? extends T>)psiClone()))
			insert(indexValue++, obj);
	}

	@Override
	default public void psiPrepend(T obj)
		throws PsiException
	{
		insert(0, obj);
	}

	@Override
	default public void psiPrependAll(PsiIterable<? extends T> iterable)
		throws PsiException
	{
		psiInsertAll(PsiInteger.ZERO, iterable);
	}

	@Override
	default public PsiArraylike<T> psiReplicate(final PsiInteger count)
		throws PsiException
	{
		long countValue=count.longValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		if(countValue>Integer.MAX_VALUE)
			throw new PsiException("limitcheck");
		PsiArraylike<T> result=(PsiArraylike<T>)psiNewEmpty();
		while(countValue-->0)
			result.psiAppendAll(this);
		return result;
	}

	default public void psiPutInterval(PsiInteger index, PsiIterable<? extends T> iterable)
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
	default public T psiExtract(final PsiInteger index)
		throws PsiException
	{
		return extract(index.intValue());
	}

	@Override
	default public PsiArraylike<T> psiGetAll(PsiIterable<PsiInteger> iterable)
		throws PsiException
	{
		PsiArraylike<T> result=(PsiArraylike<T>)psiNewEmpty();
		for(PsiInteger integer: iterable)
			result.psiAppend(psiGet(integer));
		return result;
	}

	public void psiSetLength(PsiInteger length)
		throws PsiException;

	public T extract(int indexValue)
		throws PsiException;

	public PsiArraylike<T> psiExtractInterval(PsiInteger index, PsiInteger count)
		throws PsiException;

	@Override
	public PsiArraylike<T> psiSlice(PsiIterable<PsiInteger> indices)
		throws PsiException;

	@Override
	default public String toSyntaxString()
	{
		return "["+toSyntaxStringHelper(this)+"]";
	}

	//@Override
	default public String toSyntaxStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder();
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toSyntaxString());
				else
					sb.append(obj.toSyntaxString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
