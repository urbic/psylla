package coneforest.psi;

abstract public class PsiComplexNumeric
	extends PsiObject
	implements
		PsiAtomic,
		PsiArithmetic<PsiComplexNumeric>
{
	abstract public PsiNumeric psiRe();

	abstract public PsiNumeric psiIm();

	abstract public PsiNumeric psiArg();

	abstract public PsiComplexNumeric psiConjugate();

	@Override
	abstract public PsiComplexNumeric psiNeg();

	@Override
	abstract public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn);

	@Override
	abstract public PsiComplexNumeric psiSub(final PsiComplexNumeric numeric);

	@Override
	abstract public PsiComplexNumeric psiMul(final PsiComplexNumeric numeric);


	@Override
	abstract public PsiComplexNumeric psiDiv(final PsiComplexNumeric numeric);

	@Override
	abstract public PsiNumeric psiAbs();

	@Override
	abstract public PsiComplexNumeric psiSignum();

	abstract public PsiComplexNumeric psiExp();

	/*
	public PsiReal cos()
	{
		return new PsiReal(Math.cos(getValue().doubleValue()));
	}

	public PsiReal sin()
	{
		return new PsiReal(Math.sin(getValue().doubleValue()));
	}

	public PsiReal tan()
	{
		return new PsiReal(Math.tan(getValue().doubleValue()));
	}
	*/

	abstract public PsiComplexNumeric psiCosh();

	abstract public PsiComplexNumeric psiSinh();

	/*
	public PsiReal tanh()
	{
		return new PsiReal(Math.tanh(getValue().doubleValue()));
	}
	*/

	//abstract public PsiNumeric floor();

	//abstract public PsiNumeric ceiling();

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiComplexNumeric
				&& psiRe().psiEq(((PsiComplexNumeric)obj).psiRe()).booleanValue()
				&& psiIm().psiEq(((PsiComplexNumeric)obj).psiIm()).booleanValue());
	}
}
