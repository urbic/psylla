package coneforest.psi;

public interface PsiComplexNumeric
	extends
		PsiAtomic,
		PsiArithmetic<PsiComplexNumeric>
{
	@Override
	default public String getTypeName()
	{
		return "complexnumeric";
	}

	public PsiBoolean psiIsZero();

	default public PsiBoolean psiNotZero()
	{
		return psiIsZero().psiNot();
	}

	public PsiNumeric psiRe();

	public PsiNumeric psiIm();

	public PsiNumeric psiArg()
		throws PsiException;

	public PsiComplexNumeric psiConjugate();

	default public PsiComplexNumeric psiPow(PsiComplexNumeric cn)
		throws PsiException
	{
		if(psiIsZero().booleanValue() && cn.psiNotZero().booleanValue())
			return this;
		return cn.psiMul(psiLog()).psiExp();
	}

	public PsiComplexNumeric psiExp();

	public PsiComplexNumeric psiCos();

	public PsiComplexNumeric psiSin();

	public PsiComplexNumeric psiTan();

	public PsiComplexNumeric psiLog()
		throws PsiException;

	public PsiComplexNumeric psiAtan()
		throws PsiException;

	public PsiComplexNumeric psiSqrt();

	public PsiComplexNumeric psiCbrt();

	public PsiComplexNumeric psiCosh();

	public PsiComplexNumeric psiSinh();

	public PsiComplexNumeric psiTanh();

	@Override
	default public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiComplexNumeric
				&& psiRe().psiEq(((PsiComplexNumeric)obj).psiRe()).booleanValue()
				&& psiIm().psiEq(((PsiComplexNumeric)obj).psiIm()).booleanValue());
	}
}
