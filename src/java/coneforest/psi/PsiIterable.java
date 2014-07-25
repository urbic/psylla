package coneforest.psi;

public interface PsiIterable<T extends PsiObject>
	extends Iterable<T>
{
	public java.util.Iterator<T> iterator();
}
