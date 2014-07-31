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
		// TODO
		return "-set-";
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
		throws PsiException
	{
		for(T obj: iterable)
			psiRemove(obj);
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

	//@Override
	//abstract public PsiBoolean psiContains(T obj);

	@Override
	public PsiBoolean psiIntersects(PsiSetlike<? extends T> setlike)
	{
		for(T obj: setlike)
			if(psiContains(obj).getValue())
				return new PsiBoolean(true);
		return new PsiBoolean(false);
	}
}
