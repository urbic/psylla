package coneforest.psi;

public interface PsiSetlike<T extends PsiObject>
	extends
		PsiContainer<T>,
		PsiAppendable<T>,
		PsiClearable
{
	@Override
	default public String getTypeName()
	{
		return "set";
	}

	public void psiRemove(T obj);

	default public void psiRemoveAll(PsiIterable<? extends T> iterable)
	{
		if(this==iterable)
			psiClear();
		else
			for(T obj: iterable)
				psiRemove(obj);
	}

	default public void psiRetainAll(PsiIterable<? extends T> iterable)
		throws PsiException
	{
	//	for(T obj: this)
	//		for(T otherObj: iterable)
	//			if(!psiContains(obj).getValue())
	//				psiRemove(obj);
		System.out.println("NOP RETAINALL ITERABLE");
	}

	//public void psiRetainAll(PsiSetlike<? extends T> setlike)
	//	throws PsiException;

	public PsiBoolean psiContains(T obj);

	@Override
	default public void psiClear()
	{
		for(T obj: this)
			psiRemove(obj);
	}

	@Override
	default public void psiAppendAll(final PsiIterable<? extends T> iterable)
		throws PsiException
	{
		if(this==iterable)
			return;
		for(T obj: iterable)
			psiAppend(obj);
	}

	default public PsiBoolean psiIntersects(final PsiSetlike<? extends T> setlike)
	{
		for(T obj: setlike)
			if(psiContains(obj).booleanValue())
				return PsiBoolean.TRUE;
		return PsiBoolean.FALSE;
	}

	@Override
	default public PsiSetlike<T> psiReplicate(PsiInteger count)
		throws PsiException
	{
		long countValue=count.longValue();
		if(countValue<0)
			throw new PsiException("rangecheck");
		if(countValue>Integer.MAX_VALUE)
			throw new PsiException("limitcheck");
		if(countValue==0)
			return (PsiSetlike<T>)psiNewEmpty();
		return (PsiSetlike<T>)psiClone();
	}
	
	@Override
	default public String toSyntaxString()
	{
		return "("+toSyntaxStringHelper(this)+")";
	}

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
