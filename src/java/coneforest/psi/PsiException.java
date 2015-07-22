package coneforest.psi;

public class PsiException
	extends Exception
	implements PsiObject
{
	public PsiException(final String kind)
	{
		this.kind=kind;
	}

	public String kind()
	{
		return kind;
	}

	@Override
	public PsiBoolean psiIsA(PsiStringlike stringlike)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PsiString psiToString()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PsiName psiToName()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PsiException psiClone()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PsiBoolean psiNe(PsiObject obj)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PsiBoolean psiEq(PsiObject obj)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void invoke(Interpreter interpreter)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void execute(Interpreter interpreter)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public PsiCommand psiType()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTypeName()
	{
		throw new UnsupportedOperationException();
	}

	private String kind;
}
