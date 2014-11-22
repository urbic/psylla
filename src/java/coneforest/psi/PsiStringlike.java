package coneforest.psi;

public interface PsiStringlike
	extends
		PsiLengthy,
		PsiScalar<PsiStringlike>,
		PsiConvertableToName,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();
}
