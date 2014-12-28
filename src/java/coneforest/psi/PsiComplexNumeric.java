package coneforest.psi;

abstract public class PsiComplexNumeric
	extends PsiObject
	implements
		PsiAtomic,
		PsiArithmetic<PsiComplexNumeric>
{
	abstract public PsiBoolean psiIsZero();

	abstract public PsiNumeric psiRe();

	abstract public PsiNumeric psiIm();

	abstract public PsiNumeric psiArg()
		throws PsiException;

	abstract public PsiComplexNumeric psiConjugate();

	public PsiComplexNumeric psiPow(PsiComplexNumeric cn)
		throws PsiException
	{
		return cn.psiMul(psiLog()).psiExp();
	}

	abstract public PsiComplexNumeric psiExp();

	abstract public PsiComplexNumeric psiCos();

	abstract public PsiComplexNumeric psiSin();

	abstract public PsiComplexNumeric psiTan();

	abstract public PsiComplexNumeric psiLog()
		throws PsiException;

	public PsiComplexNumeric psiAtan()
		throws PsiException
	{
		throw new UnsupportedOperationException();
	}

	abstract public PsiComplexNumeric psiSqrt();

	abstract public PsiComplexNumeric psiCbrt();

	abstract public PsiComplexNumeric psiCosh();

	abstract public PsiComplexNumeric psiSinh();

	abstract public PsiComplexNumeric psiTanh();
	/*{
		return psiSinh().psiDiv(psiCosh());
	}*/

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiComplexNumeric
				&& psiRe().psiEq(((PsiComplexNumeric)obj).psiRe()).booleanValue()
				&& psiIm().psiEq(((PsiComplexNumeric)obj).psiIm()).booleanValue());
	}
}
