package coneforest.psi.core;

public class PsiUnsupportedException
	extends PsiException
{
	@Override
	public String getName()
	{
		return "unsupported";
	}
}
