package coneforest.psi.core;

public interface PsiConvertableToInteger
	extends PsiObject
{
	public PsiInteger psiToInteger()
		throws PsiException;
}
