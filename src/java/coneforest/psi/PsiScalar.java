package coneforest.psi;

public interface PsiScalar<T extends PsiScalar>
{
	public PsiBoolean lt(T scalar);
	
	public PsiBoolean le(T scalar);

	public PsiBoolean gt(T scalar);
	
	public PsiBoolean ge(T scalar);
}
