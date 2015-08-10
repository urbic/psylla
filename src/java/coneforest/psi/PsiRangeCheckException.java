package coneforest.psi;

public class PsiRangeCheckException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="rangecheck";
}
