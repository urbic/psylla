package coneforest.psi;

public class PsiConcurrentModificationException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="concurrentmodification";
}
