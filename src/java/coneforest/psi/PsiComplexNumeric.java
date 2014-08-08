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

	public PsiComplexNumeric psiPow(PsiComplexNumeric cn)
		throws PsiException
	{
		return cn.psiMul(psiLog()).psiExp();
	}

	abstract public PsiComplexNumeric psiExp();

	abstract public PsiComplexNumeric psiLog()
		throws PsiException;

	public PsiComplexNumeric psiAtan()
		throws PsiException
	{
		throw new UnsupportedOperationException();
	}

	abstract public PsiComplexNumeric psiSqrt();

	abstract public PsiComplexNumeric psiCbrt();


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

	public PsiComplexNumeric psiTanh()
	{
		return psiSinh().psiDiv(psiCosh());
	}

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiComplexNumeric
				&& psiRe().psiEq(((PsiComplexNumeric)obj).psiRe()).booleanValue()
				&& psiIm().psiEq(((PsiComplexNumeric)obj).psiIm()).booleanValue());
	}
}
