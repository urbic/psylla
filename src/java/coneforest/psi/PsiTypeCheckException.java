package coneforest.psi;

public class PsiTypeCheckException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="typecheck";
}
