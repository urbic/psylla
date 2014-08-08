package coneforest.psi;

abstract public class PsiNumeric
	extends PsiComplexNumeric
	implements
		PsiConvertableToInteger,
		PsiConvertableToReal,
		PsiAtomic,
		PsiScalar<PsiNumeric>
{
	abstract int intValue();

	abstract long longValue();

	abstract double doubleValue();

	@Override
	public PsiInteger psiToInteger()
		throws PsiException
	{
		if(doubleValue()>=Long.MIN_VALUE
				&& doubleValue()<=Long.MAX_VALUE)
		{
			return new PsiInteger(longValue());
		}
		else
			throw new PsiException("rangecheck");
	}

	@Override
	public PsiReal psiToReal()
		throws PsiException
	{
		return new PsiReal(doubleValue());
	}

	@Override
	public PsiReal psiArg()
	{
		if(doubleValue()>0.D)
			return new PsiReal(0.D);
		else if(doubleValue()<0.D)
			return new PsiReal(Math.PI);
		else
			return new PsiReal(1.D/0.D);
	}

	@Override
	abstract public PsiNumeric psiNeg();

	@Override
	public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiAdd(cn);
	}

	@Override
	public PsiComplexNumeric psiSub(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiSub(cn);
	}

	/*
	@Override
	abstract public PsiNumeric psiMul(final PsiNumeric numeric);
	*/

	@Override
	public PsiComplexNumeric psiMul(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiMul(cn);
	}

	public PsiReal psiDiv(final PsiNumeric numeric)
	{
		return new PsiReal(doubleValue()/numeric.doubleValue());
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

	@Override
	public PsiComplexNumeric psiSqrt()
	{
		if(doubleValue()>=0.D)
			return new PsiReal(Math.sqrt(doubleValue()));
		else
			return new PsiComplex(0, Math.sqrt(-doubleValue()));
	}

	@Override
	public PsiComplexNumeric psiCbrt()
	{
		return new PsiReal(Math.cbrt(doubleValue()));
	}

	@Override
	public PsiReal psiExp()
	{
		return new PsiReal(Math.exp(doubleValue()));
	}

	@Override
	public PsiComplexNumeric psiLog()
		throws PsiException
	{
		if(doubleValue()>0.D)
			return new PsiReal(Math.log(doubleValue()));
		else if(doubleValue()<0.D)
			return new PsiComplex((PsiNumeric)psiAbs().psiLog(), new PsiReal(Math.PI));
		else
			throw new PsiException("undefinedresult");
	}


	//@Override
	public PsiReal cos()
	{
		return new PsiReal(Math.cos(doubleValue()));
	}

	//@Override
	public PsiReal sin()
	{
		return new PsiReal(Math.sin(doubleValue()));
	}

	//@Override
	public PsiReal tan()
	{
		return new PsiReal(Math.tan(doubleValue()));
	}

	@Override
	public PsiReal psiCosh()
	{
		return new PsiReal(Math.cosh(doubleValue()));
	}

	@Override
	public PsiReal psiSinh()
	{
		return new PsiReal(Math.sinh(doubleValue()));
	}

	@Override
	public PsiReal psiTanh()
	{
		return new PsiReal(Math.tanh(doubleValue()));
	}

	@Override
	public PsiReal psiAtan()
	{
		return new PsiReal(Math.atan(doubleValue()));
	}

	public PsiReal hypot(PsiNumeric numeric)
	{
		return new PsiReal(Math.hypot(doubleValue(), numeric.doubleValue()));
	}

	abstract public PsiNumeric psiFloor();

	abstract public PsiNumeric psiCeiling();

	@Override
	public PsiBoolean psiEq(final PsiObject obj)
	{
		return new PsiBoolean(obj instanceof PsiNumeric
				&& doubleValue()==((PsiNumeric)obj).doubleValue());
	}

	@Override
	public PsiBoolean psiLt(final PsiNumeric numeric)
	{
		return new PsiBoolean(doubleValue()<numeric.doubleValue());
	}

	@Override
	public PsiBoolean psiLe(final PsiNumeric numeric)
	{
		return new PsiBoolean(doubleValue()<=numeric.doubleValue());
	}

	@Override
	public PsiBoolean psiGt(final PsiNumeric numeric)
	{
		return new PsiBoolean(doubleValue()>numeric.doubleValue());
	}

	@Override
	public PsiBoolean psiGe(final PsiNumeric numeric)
	{
		return new PsiBoolean(doubleValue()>=numeric.doubleValue());
	}
}
