package coneforest.psi;

abstract public class PsiNumeric
	//extends PsiObject
	extends PsiComplexNumeric
	//implements PsiAtomic, PsiArithmetic<PsiNumeric>, PsiScalar<PsiNumeric>
	implements
		PsiConvertableToInteger,
		PsiConvertableToReal,
		PsiAtomic,
		PsiScalar<PsiNumeric>
{
	abstract Number getValue();

	abstract int intValue();

	abstract long longValue();

	abstract double doubleValue();

	@Override
	public PsiInteger psiToInteger()
		throws PsiException
	{
		if(getValue().doubleValue()>=Long.MIN_VALUE
				&& getValue().doubleValue()<=Long.MAX_VALUE)
		{
			return new PsiInteger(getValue().longValue());
		}
		else
			throw new PsiException("rangecheck");
	}

	@Override
	public PsiReal psiToReal()
		throws PsiException
	{
		return new PsiReal(getValue().doubleValue());
	}

	@Override
	public PsiReal psiArg()
	{
		if(getValue().doubleValue()>0.D)
			return new PsiReal(0.D);
		else if(getValue().doubleValue()<0.D)
			return new PsiReal(Math.PI);
		else
			return new PsiReal(1.D/0.D);
	}

	@Override
	abstract public PsiNumeric psiNeg();

	///*
	@Override
	public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiAdd(cn);
	}
	//*/

	// TODO
	abstract public PsiNumeric psiSub(final PsiNumeric numeric);

	@Override
	public PsiComplexNumeric psiSub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiSub(cn);
	}

	abstract public PsiNumeric psiMul(final PsiNumeric numeric);

	@Override
	public PsiComplexNumeric psiMul(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiMul(cn);
	}

	public PsiReal psiDiv(final PsiNumeric numeric)
	{
		return new PsiReal(getValue().doubleValue()/numeric.getValue().doubleValue());
	}

	@Override
	public PsiComplexNumeric psiDiv(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiDiv((PsiNumeric)cn);
		return new PsiComplex(this).psiDiv(cn);
	}

	abstract public PsiNumeric pow(final PsiNumeric numeric);

	@Override
	public abstract PsiNumeric psiAbs();

	//@Override
	public PsiReal sqrt()
	{
		return new PsiReal(Math.sqrt(getValue().doubleValue()));
	}

	//@Override
	public PsiReal cbrt()
	{
		return new PsiReal(Math.cbrt(getValue().doubleValue()));
	}

	//@Override
	public PsiReal log()
	{
		return new PsiReal(Math.log(getValue().doubleValue()));
	}

	@Override
	public PsiReal psiExp()
	{
		return new PsiReal(Math.exp(getValue().doubleValue()));
	}

	//@Override
	public PsiReal cos()
	{
		return new PsiReal(Math.cos(getValue().doubleValue()));
	}

	//@Override
	public PsiReal sin()
	{
		return new PsiReal(Math.sin(getValue().doubleValue()));
	}

	//@Override
	public PsiReal tan()
	{
		return new PsiReal(Math.tan(getValue().doubleValue()));
	}

	@Override
	public PsiReal psiCosh()
	{
		return new PsiReal(Math.cosh(getValue().doubleValue()));
	}

	@Override
	public PsiReal psiSinh()
	{
		return new PsiReal(Math.sinh(getValue().doubleValue()));
	}

	//@Override
	public PsiReal tanh()
	{
		return new PsiReal(Math.tanh(getValue().doubleValue()));
	}

	public PsiReal atan()
	{
		return new PsiReal(Math.atan(getValue().doubleValue()));
	}

	public PsiReal hypot(PsiNumeric numeric)
	{
		return new PsiReal(Math.hypot(getValue().doubleValue(), numeric.getValue().doubleValue()));
	}

	abstract public PsiNumeric psiFloor();

	abstract public PsiNumeric psiCeiling();

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNumeric
				&& getValue().doubleValue()==((PsiNumeric)obj).getValue().doubleValue());
	}

	@Override
	public PsiBoolean psiLt(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()<numeric.getValue().doubleValue());
	}

	@Override
	public PsiBoolean psiLe(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()<=numeric.getValue().doubleValue());
	}

	@Override
	public PsiBoolean psiGt(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()>numeric.getValue().doubleValue());
	}

	@Override
	public PsiBoolean psiGe(final PsiNumeric numeric)
	{
		return new PsiBoolean(getValue().doubleValue()>=numeric.getValue().doubleValue());
	}
}
