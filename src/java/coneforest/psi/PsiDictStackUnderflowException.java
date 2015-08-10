package coneforest.psi;

public class PsiDictStackUnderflowException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="dictstackunderflow";
}
