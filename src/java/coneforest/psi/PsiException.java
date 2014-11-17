package coneforest.psi;

public class PsiException
	extends Exception
{
	public PsiException(final String kind)
	{
		this.kind=kind;
	}

	public PsiException()
	{
		this.kind=getClass().getSimpleName().substring(1);
	}

	public String kind()
	{
		return kind;
	}

	private String kind;

	public class _typecheck extends PsiException {}
	public class _stackunderflow extends PsiException {}
}
