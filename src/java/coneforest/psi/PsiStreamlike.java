package coneforest.psi;

public interface PsiStreamlike<T extends PsiObject>
	extends PsiObject
{
	public PsiInteger psiCount();

	public PsiStreamlike<T> psiConcat(PsiStreamlike<T> streamlike);
}
