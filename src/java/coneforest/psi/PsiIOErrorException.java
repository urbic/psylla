package coneforest.psi;

public class PsiIOErrorException
	extends PsiException
{
	@Override
	public String getName()
	{
		return NAME;
	}

	public final String NAME="ioerror";
}
