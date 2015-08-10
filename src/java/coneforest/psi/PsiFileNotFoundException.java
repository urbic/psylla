package coneforest.psi;

public class PsiFileNotFoundException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="filenotfound";
}
