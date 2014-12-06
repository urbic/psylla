package coneforest.psi;

public interface PsiArithmetic<T extends PsiArithmetic>
{
	public T psiNeg();

	public PsiNumeric psiAbs();

	public T psiSignum();

	public T psiAdd(T arithmetic);

	public T psiSub(T arithmetic);

	public T psiMul(T arithmetic);

	//public T psiMul(PsiLinear<? extends T> linear);

	public T psiDiv(T arithmetic);
}
