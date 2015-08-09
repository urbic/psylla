package coneforest.psi;

abstract public class PsiComplexNumeric
	implements
		PsiAtomic,
		PsiArithmetic<PsiComplexNumeric>
{
	@Override
	public String getTypeName()
	{
		return "complexnumeric";
	}

	abstract public PsiComplexNumeric psiZero();

	abstract public PsiBoolean psiIsZero();

	public PsiBoolean psiNotZero()
	{
		return psiIsZero().psiNot();
	}

	abstract public PsiNumeric psiRe();

	abstract public PsiNumeric psiIm();

	abstract public PsiNumeric psiArg()
		throws PsiException;

	abstract public PsiComplexNumeric psiConjugate();

	public PsiComplexNumeric psiPow(PsiComplexNumeric cn)
		throws PsiException
	{
		if(psiIsZero().booleanValue() && cn.psiNotZero().booleanValue())
			return psiZero();
		return cn.psiMul(psiLog()).psiExp();
	}

	abstract public PsiComplexNumeric psiExp();

	abstract public PsiComplexNumeric psiCos();

	abstract public PsiComplexNumeric psiSin();

	abstract public PsiComplexNumeric psiTan();

	abstract public PsiComplexNumeric psiLog()
		throws PsiException;

	abstract public PsiComplexNumeric psiAtan()
		throws PsiException;

	abstract public PsiComplexNumeric psiSqrt();

	abstract public PsiComplexNumeric psiCbrt();

	abstract public PsiComplexNumeric psiCosh();

	abstract public PsiComplexNumeric psiSinh();

	abstract public PsiComplexNumeric psiTanh();

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiComplexNumeric
				&& psiRe().psiEq(((PsiComplexNumeric)obj).psiRe()).booleanValue()
				&& psiIm().psiEq(((PsiComplexNumeric)obj).psiIm()).booleanValue());
	}
}
