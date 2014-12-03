package coneforest.psi;

public interface PsiScalar<T extends PsiScalar>
{
	public PsiBoolean psiLt(T scalar);

	public PsiBoolean psiLe(T scalar);

	public PsiBoolean psiGt(T scalar);

	public PsiBoolean psiGe(T scalar);
	
	public PsiInteger psiCmp(T scalar);
}
