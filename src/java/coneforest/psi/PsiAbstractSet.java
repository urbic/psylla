package coneforest.psi;

abstract public class PsiAbstractSet<T extends PsiObject>
	extends PsiObject
	implements PsiSetlike<T>
{
	public String getTypeName()
	{
		return "set";
	}

	public String toString()
	{
		return "-set-";
	}

	public void appendAll(PsiSetlike<? extends T> setlike)
		throws PsiException
	{
		for(T obj: setlike)
			append(obj);
	}

	public void removeAll(PsiSetlike<? extends T> setlike)
		throws PsiException
	{
		for(T obj: setlike)
			remove(obj);
	}
}
