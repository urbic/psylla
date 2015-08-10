package coneforest.psi;

public class PsiFileExistsException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="fileexists";
}
