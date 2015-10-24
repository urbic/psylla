package coneforest.psi;

public class PsiNotDirectoryException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="notdirectory";
}
