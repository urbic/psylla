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
}
