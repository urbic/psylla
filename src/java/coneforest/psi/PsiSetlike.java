package coneforest.psi;

public interface PsiSetlike<T extends PsiObject>
	extends PsiIterable<T>, PsiComposite<T>
{
	public void append(T obj)
		throws PsiException;

	public void appendAll(PsiSetlike<T> setlike)
		throws PsiException;
	
	public void remove(T obj)
		throws PsiException;

	public void removeAll(PsiSetlike<T> setlike)
		throws PsiException;

	public PsiBoolean isEmpty();
}
