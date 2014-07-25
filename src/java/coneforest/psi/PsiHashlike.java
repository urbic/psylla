package coneforest.psi;

public interface PsiHashlike<T extends PsiObject>
	extends PsiComposite
{
	public T get(PsiStringlike key)
		throws PsiException;

	public void put(PsiStringlike key, T obj)
		throws PsiException;

	public void undef(PsiStringlike key);
	
	public PsiBoolean known(PsiStringlike key);

	public java.util.Iterator<java.util.Map.Entry<String, T>> iterator();
}
