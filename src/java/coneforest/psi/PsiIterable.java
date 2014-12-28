package coneforest.psi;

public interface PsiIterable<T>
	extends
		PsiObject,
		Iterable<T>
{
	public java.util.Iterator<T> iterator();
}
