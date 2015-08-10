package coneforest.psi;

public class PsiUndefinedException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="undefined";
}
