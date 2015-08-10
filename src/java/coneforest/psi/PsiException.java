package coneforest.psi;

abstract public class PsiException
	extends Exception
	implements PsiObject
{
	@Override
	public String getTypeName()
	{
		return "exception";
	}

	public PsiException()
	{
	}

	abstract public String getName();
}
