package coneforest.psi;

public class PsiException extends Exception
{
	public PsiException(final String kind)
	{
		this.kind=kind;
	}

	public String kind()
	{
		return kind;
	}

	public static final String
		IOERROR="ioerror",
		RANGECHECK="rangecheck",
		TYPECHECK="typecheck"
		;

	private String kind;
}
