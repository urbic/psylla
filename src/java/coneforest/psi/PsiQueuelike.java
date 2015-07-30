package coneforest.psi;

public interface PsiQueuelike<T extends PsiObject>
	extends
		PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "queuelike";
	}

	public T psiTake()
		throws PsiException;

	public void psiGive(T obj)
		throws PsiException;
}
