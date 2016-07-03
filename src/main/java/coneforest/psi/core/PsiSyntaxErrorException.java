package coneforest.psi.core;

public class PsiSyntaxErrorException
	extends PsiException
{
	public PsiSyntaxErrorException()
	{
		super();
	}

	public PsiSyntaxErrorException(PsiObject oEmitter)
	{
		super(oEmitter);
	}

	@Override
	public String getName()
	{
		return "syntaxerror";
	}
}
