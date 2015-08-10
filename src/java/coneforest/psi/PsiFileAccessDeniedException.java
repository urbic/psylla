package coneforest.psi;

public class PsiFileAccessDeniedException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="fileaccessdenied";
}
