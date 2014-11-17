package coneforest.psi;

public interface PsiLengthy<T extends PsiObject>
{
	public int length();

	public boolean isEmpty();

	public PsiInteger psiLength();

	public PsiBoolean psiIsEmpty();

	public String toString(PsiLengthy composite);
}
