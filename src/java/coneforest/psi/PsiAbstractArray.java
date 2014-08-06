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
	public void psiPutInterval(PsiInteger index, PsiIterable<? extends T> iterable)
		throws PsiException
	{
		int indexValue=index.getValue().intValue();
		if(indexValue<0
			||
			iterable instanceof PsiComposite
			&& indexValue+((PsiComposite)iterable).length()>=length())
			throw new PsiException("rangecheck");
		for(T obj: iterable)
		{
			psiPut(indexValue++, obj);
			if(indexValue==length())
				break;
		}
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

	public String toString(PsiComposite composite)
	{
		StringBuilder sb=new StringBuilder(isExecutable()? "{": "[");
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiComposite)
					sb.append(obj==composite? "-"+obj.getTypeName()+"-": ((PsiComposite)obj).toString(this));
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(isExecutable()? '}': ']');
		return sb.toString();
	}

	/*
	@Override
	public String toString()
	{
		java.util.HashSet<PsiComposite> processed=new java.util.HashSet<PsiComposite>();
		processed.add(this);
		StringBuilder sb=new StringBuilder(isExecutable()? "{": "[");
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiComposite)
					toStringHelper((PsiComposite)obj, sb, processed);
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(isExecutable()? '}': ']');
		return sb.toString();
	}
	*/
}
