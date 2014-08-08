package coneforest.psi;

public interface PsiStringlike
	extends
		PsiLengthy<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiConvertableToName,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();
}
