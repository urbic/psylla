package coneforest.psi;

public interface PsiContainer<T extends PsiObject>
	extends
		PsiClearable,
		PsiIterable<T>,
		PsiLengthy
{
	@Override
	default public String getTypeName()
	{
		return "container";
	}

	default public PsiContainer<T> psiNewEmpty()
		throws PsiException
	{
		try
		{
			return getClass().newInstance();
		}
		catch(InstantiationException|IllegalAccessException e)
		{
			throw new PsiUnsupportedException();
		}
	}
}
