package coneforest.psi;

public class PsiInvalidRegExpException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="invalidregexp";
}
