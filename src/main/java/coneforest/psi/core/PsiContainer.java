package coneforest.psi.core;

@coneforest.psi.Type("container")
public interface PsiContainer<T extends PsiObject>
	extends
		PsiClearable,
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
		catch(final InstantiationException|IllegalAccessException e)
		{
			throw new PsiUnsupportedException();
		}
	}
}
