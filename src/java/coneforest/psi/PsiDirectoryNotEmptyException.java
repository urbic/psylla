package coneforest.psi;

public class PsiDirectoryNotEmptyException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="directorynotempty";
}
