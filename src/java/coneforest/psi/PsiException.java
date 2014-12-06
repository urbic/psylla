package coneforest.psi;

public class PsiException
	extends Exception
{
	public PsiException(final String kind)
	{
		this.kind=kind;
	}

	public String kind()
	{
		return kind;
	}

	private String kind;
}
