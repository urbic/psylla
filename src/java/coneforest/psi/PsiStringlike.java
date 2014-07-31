package coneforest.psi;

public interface PsiStringlike
	extends
		PsiComposite<PsiInteger>,
		PsiScalar<PsiStringlike>
{
	public String getString();
}
