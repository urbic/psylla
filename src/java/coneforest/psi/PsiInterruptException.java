package coneforest.psi;

public class PsiInterruptException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="interrupt";
}
