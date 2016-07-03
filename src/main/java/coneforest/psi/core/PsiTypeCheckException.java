package coneforest.psi.core;

public class PsiTypeCheckException
	extends PsiException
{
	public PsiTypeCheckException()
	{
		super();
	}

	public PsiTypeCheckException(PsiObject oEmitter)
	{
		super(oEmitter);
	}

	@Override
	public String getName()
	{
		return "typecheck";
	}
}
