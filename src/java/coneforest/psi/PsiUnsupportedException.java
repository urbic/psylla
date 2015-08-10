package coneforest.psi;

public class PsiUnsupportedException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="unsupported";
}
