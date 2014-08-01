package coneforest.psi;

public interface PsiIterable<T>
	extends Iterable<T>
{
	public java.util.Iterator<T> iterator();

	//public PsiArray psiToArray();
	
	//public PsiSet psiToSet();
}
