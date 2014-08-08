package coneforest.psi;

abstract public class PsiAbstractSet<T extends PsiObject>
	extends PsiObject
	implements PsiSetlike<T>
{
	@Override
	public String getTypeName()
	{
		return "set";
	}

	
	@Override
	public String toString()
	{
		return toString(this);
	}

	public String toString(PsiLengthy composite)
	{
		StringBuilder sb=new StringBuilder("(");
		if(length()>0)
		{
			for(PsiObject obj: this)
			{
				if(obj instanceof PsiLengthy)
					sb.append(obj==composite? "-"+obj.getTypeName()+"-": ((PsiLengthy)obj).toString(this));
				else
					sb.append(obj.toString());
				sb.append(' ');
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(')');
		return sb.toString();
	}

	@Override
	public void psiAppendAll(PsiIterable<? extends T> iterable)
		throws PsiException
	{
		for(T obj: iterable)
			psiAppend(obj);
	}

	@Override
	public void psiRemoveAll(PsiIterable<? extends T> iterable)
	{
		for(T obj: iterable)
			psiRemove(obj);
	}

	@Override
	public void psiRetainAll(PsiIterable<? extends T> iterable)
		throws PsiException
	{
	//	for(T obj: this)
	//		for(T otherObj: iterable)
	//			if(!psiContains(obj).getValue())
	//				psiRemove(obj);
		System.out.println("NOP RETAINALL ITERABLE");
	}

	@Override
	public void psiRetainAll(PsiSetlike<? extends T> setlike)
	{
		System.out.println("NOP RETAINALL SETLIKE");
	}

	@Override
	public PsiInteger psiLength()
	{
		return new PsiInteger(length());
	}

	@Override
	public boolean isEmpty()
	{
		return length()==0;
	}

	@Override
	public PsiBoolean psiIsEmpty()
	{
		return new PsiBoolean(isEmpty());
	}

	@Override
	public void psiClear()
	{
		for(T obj: this)
			psiRemove(obj);
	}

	@Override
	public PsiBoolean psiIntersects(PsiSetlike<? extends T> setlike)
	{
		for(T obj: setlike)
			if(psiContains(obj).booleanValue())
				return new PsiBoolean(true);
		return new PsiBoolean(false);
	}
}
