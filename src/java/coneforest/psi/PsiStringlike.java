package coneforest.psi;

public interface PsiStringlike
	extends
		//PsiLengthy,
		//PsiIterable<PsiInteger>,
		PsiContainer<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiConvertableToName,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();
}
