package coneforest.psi.core;

public interface PsiConvertableToReal
	extends PsiObject
{
	public PsiReal psiToReal()
		throws PsiException;
}
