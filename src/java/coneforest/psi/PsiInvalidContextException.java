package coneforest.psi;

public class PsiInvalidContextException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="invalidcontext";
}
