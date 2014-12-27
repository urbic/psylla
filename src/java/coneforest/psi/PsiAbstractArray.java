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
		int indexValue=index.intValue();
		for(T obj: iterable)
			psiInsert(indexValue++, obj);
	}

	@Override
	public T psiDelete(PsiInteger index)
		throws PsiException
	{
		return psiDelete(index.intValue());
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
		return toString(this);
	}

	public String toString(PsiLengthy lengthy)
	{
		StringBuilder sb=new StringBuilder(isExecutable()? "{": "[");
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==lengthy? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toString(this));
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
