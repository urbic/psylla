package coneforest.psi;

public class PsiInvalidStateException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="invalidstate";
}
