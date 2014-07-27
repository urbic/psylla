package coneforest.psi;

public interface PsiArithmetic<T extends PsiArithmetic>
{
	public T neg();
	
	public PsiNumeric abs();
	
	public T signum();
	
	public T add(T arithmetic);
	
	public T sub(T arithmetic);
	
	public T mul(T arithmetic);
	
	public T div(T arithmetic);
}
