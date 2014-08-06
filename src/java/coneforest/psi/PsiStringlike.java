package coneforest.psi;

public interface PsiStringlike
	extends
		PsiComposite<PsiInteger>,
		PsiScalar<PsiStringlike>,
		PsiConvertableToName,
		PsiConvertableToInteger,
		PsiConvertableToReal
{
	public String getString();
}
