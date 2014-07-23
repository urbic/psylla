package coneforest.psi;

public interface PsiArithmetic<T extends PsiArithmetic>
{
	public T neg();
	
	public T abs();
	
	public T add(T arithmetic);
	
	public T sub(T arithmetic);
	
	public T mul(T arithmetic);
	
	public T div(T arithmetic);
	
}
