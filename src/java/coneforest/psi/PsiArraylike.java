package coneforest.psi;

public interface PsiArraylike<T extends PsiObject>
	extends Iterable<T>, PsiComposite
{
	public T get(PsiInteger oIndex)
		throws PsiException;

	public void put(PsiInteger oIndex, T obj)
		throws PsiException;

	public java.util.Iterator<T> iterator();
}
