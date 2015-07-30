package coneforest.psi;

public interface PsiPrependable<T extends PsiObject>
	extends PsiObject
{
	@Override
	default public String getTypeName()
	{
		return "prendable";
	}

	public void psiPrepend(T obj)
		throws PsiException;

	public void psiPrependAll(PsiIterable<? extends T> setlike)
		throws PsiException;
}
