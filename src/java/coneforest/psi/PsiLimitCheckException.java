package coneforest.psi;

public class PsiLimitCheckException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="limitcheck";
}
