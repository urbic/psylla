package coneforest.psi;

public interface PsiQueuelike<T extends PsiObject>
	extends
		PsiObject
{
	public T psiTake()
		throws PsiException;

	public void psiGive(T obj)
		throws PsiException;
}
