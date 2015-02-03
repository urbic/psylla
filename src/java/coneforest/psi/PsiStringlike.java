package coneforest.psi;

public interface PsiStringlike
	extends
		PsiContainer<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiEvaluable,
		PsiConvertableToName,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();
}
