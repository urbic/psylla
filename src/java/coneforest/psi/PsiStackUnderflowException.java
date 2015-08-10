package coneforest.psi;

public class PsiStackUnderflowException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="stackunderflow";
}
