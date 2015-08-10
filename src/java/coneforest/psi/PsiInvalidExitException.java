package coneforest.psi;

public class PsiInvalidExitException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="invalidexit";
}
