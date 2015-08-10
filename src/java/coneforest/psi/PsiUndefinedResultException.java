package coneforest.psi;

public class PsiUndefinedResultException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="undefinedresult";
}
