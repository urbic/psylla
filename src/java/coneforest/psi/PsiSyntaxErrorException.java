package coneforest.psi;

public class PsiSyntaxErrorException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="syntaxerror";
}
