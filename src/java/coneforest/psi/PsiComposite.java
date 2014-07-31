package coneforest.psi;

public interface PsiComposite<T extends PsiObject>
{
	public int length();

	public boolean isEmpty();

	public PsiInteger psiLength();

	public PsiBoolean psiIsEmpty();
}
