package coneforest.psi;

abstract public class PsiAbstractArray<T extends PsiObject>
	extends PsiAbstractObject
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
	public PsiAbstractArray<T> psiCloneEmpty()
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
	public T psiGet(PsiInteger index)
		throws PsiException
	{
		return psiGet(index.intValue());
	}

	@Override
	public void psiPut(PsiInteger index, T obj)
		throws PsiException
	{
		psiPut(index.intValue(), obj);
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
			psiPut(indexValue++, obj);
			if(indexValue==length())
				break;
		}
	}

	@Override
	abstract public void psiInsert(int indexValue, T obj)
		throws PsiException;

	@Override
	public void psiInsert(PsiInteger index, T obj)
		throws PsiException
	{
		psiInsert(index.intValue(), obj);
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
		//for(T obj: iterable)
		for(T obj: (this!=iterable? iterable: (PsiIterable<? extends T>)psiClone()))
			psiInsert(indexValue++, obj);
	}

	@Override
	public T psiDelete(final PsiInteger index)
		throws PsiException
	{
		return psiDelete(index.intValue());
	}

	@Override
	public PsiAbstractArray<T> psiReplicate(final PsiInteger count)
		throws PsiException
	{
		int countValue=count.intValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		PsiAbstractArray<T> result=psiCloneEmpty();
		while(countValue-->0)
			result.psiAppendAll(this);
		return result;
	}

	@Override
	public void psiReverse()
		throws PsiException
	{
		int length=length();
		for(int i=0; i<(int)(length/2); i++)
		{
			T obj=psiGet(i);
			psiPut(i, psiGet(length-1-i));
			psiPut(length-1-i, obj);
		}
	}

	@Override
	public String toString()
	{
		return toStringHelper(this);
	}

	@Override
	public String toStringHelper(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder(isExecutable()? "{": "[");
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toStringHelper(this));
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(isExecutable()? '}': ']');
		return sb.toString();
	}
}
