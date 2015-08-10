package coneforest.psi;

public class PsiUnmatchedMarkException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="unmatchedmark";
}
