package coneforest.psi;

public interface PsiComposite<T extends PsiObject>
{
	public PsiInteger psiLength();

	public PsiBoolean psiIsEmpty();
}
