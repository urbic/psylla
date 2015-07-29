package coneforest.psi;

public class PsiException
	extends Exception
	implements PsiObject
{
	@Override
	public String getTypeName()
	{
		return "exception";
	}

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
