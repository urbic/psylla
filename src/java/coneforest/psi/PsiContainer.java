package coneforest.psi;

public interface PsiContainer<T extends PsiObject>
	extends
		PsiIterable<T>,
		PsiLengthy
{
	default public PsiContainer<T> psiNewEmpty()
		throws PsiException
	{
		try
		{
			return getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e)
		{
			throw new PsiException("unknownerror");
		}
	}
}
