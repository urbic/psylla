package coneforest.psi;

public class PsiUnregisteredException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="unregistered";
}
