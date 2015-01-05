package coneforest.psi;

public interface PsiStringlike
	extends
		PsiContainer<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiConvertableToName,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();
}
