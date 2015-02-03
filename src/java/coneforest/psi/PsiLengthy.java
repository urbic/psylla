package coneforest.psi;

public interface PsiLengthy
	extends PsiObject
{
	public int length();

	public boolean isEmpty();

	public PsiInteger psiLength();

	public PsiBoolean psiIsEmpty();

	public String toStringHelper(PsiLengthy lengthy);
}
