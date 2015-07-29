package coneforest.psi;

public interface PsiStringlike
	extends
		PsiContainer<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiEvaluable,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();

	public PsiStringlike psiUpperCase();
	
	public PsiStringlike psiLowerCase();
}
