package coneforest.psi.core;

/**
*	A representation of Ψ-{@code realnumeric}, an abstraction of real numbers.
*/
public interface PsiRealNumeric
	extends
		PsiNumeric,
		PsiConvertableToInteger,
		PsiConvertableToReal,
		PsiScalar<PsiRealNumeric>
{
	/**
	*	@return a string {@code "numeric"}.
	*/
	@Override
	default public String typeName()
	{
		return "numeric";
	}

	int intValue();

	long longValue();

	double doubleValue();

	@Override
	default double realValue()
	{
		return doubleValue();
	}

	@Override
	default double imagValue()
	{
		return 0.D;
	}

	public PsiRealNumeric psiSignum();

	@Override
	default public PsiInteger psiToInteger()
		throws PsiException
	{
		if(doubleValue()>=Long.MIN_VALUE
				&& doubleValue()<=Long.MAX_VALUE)
		{
			return PsiInteger.valueOf(longValue());
		}
		else
			throw new PsiRangeCheckException();
	}

	@Override
	default public PsiReal psiToReal()
		throws PsiException
	{
		return new PsiReal(doubleValue());
	}

	@Override
	public PsiRealNumeric psiNeg();

	public PsiNumeric psiAdd(final PsiRealNumeric numeric);

	@Override
	default public PsiNumeric psiAdd(final PsiNumeric oNumber)
	{
		if(oNumber instanceof PsiRealNumeric)
			return psiAdd((PsiRealNumeric)oNumber);
		return new PsiComplex(this).psiAdd(oNumber);
	}

	public PsiNumeric psiSub(final PsiRealNumeric oNumeric);

	@Override
	default public PsiNumeric psiSub(final PsiNumeric oNumber)
	{
		if(oNumber instanceof PsiRealNumeric)
			return psiSub((PsiRealNumeric)oNumber);
		return new PsiComplex(this).psiSub(oNumber);
	}

	public PsiNumeric psiMul(final PsiRealNumeric oNumeric);

	@Override
	default public PsiNumeric psiMul(final PsiNumeric oNumber)
	{
		if(oNumber instanceof PsiRealNumeric)
			return psiMul((PsiRealNumeric)oNumber);
		return new PsiComplex(this).psiMul(oNumber);
	}

	default public PsiReal psiDiv(final PsiRealNumeric oNumeric)
	{
		return new PsiReal(doubleValue()/oNumeric.doubleValue());
	}

	@Override
	default public PsiNumeric psiDiv(final PsiNumeric oNumber)
	{
		if(oNumber instanceof PsiRealNumeric)
			return psiDiv((PsiRealNumeric)oNumber);
		return new PsiComplex(this).psiDiv(oNumber);
	}

	@Override
	public PsiRealNumeric psiAbs();

	@Override
	default public PsiReal psiSqrt()
	{
		return new PsiReal(Math.sqrt(doubleValue()));
	}

	@Override
	default public PsiNumeric psiCbrt()
	{
		return new PsiReal(Math.cbrt(doubleValue()));
	}

	@Override
	default public PsiReal psiExp()
	{
		return new PsiReal(Math.exp(doubleValue()));
	}

	@Override
	default public PsiRealNumeric psiLog()
		throws PsiUndefinedResultException, PsiRangeCheckException
	{
		if(doubleValue()>0.D)
			return new PsiReal(Math.log(doubleValue()));
		else if(doubleValue()<0.D)
			//return new PsiComplex((PsiRealNumeric)psiAbs().psiLog(), new PsiReal(Math.PI));
			throw new PsiRangeCheckException();
		else
			throw new PsiUndefinedResultException();
	}

	@Override
	default public PsiReal psiCos()
	{
		return new PsiReal(Math.cos(doubleValue()));
	}

	@Override
	default public PsiReal psiSin()
	{
		return new PsiReal(Math.sin(doubleValue()));
	}

	@Override
	default public PsiReal psiTan()
	{
		return new PsiReal(Math.tan(doubleValue()));
	}

	@Override
	default public PsiReal psiCosh()
	{
		return new PsiReal(Math.cosh(doubleValue()));
	}

	@Override
	default public PsiReal psiSinh()
	{
		return new PsiReal(Math.sinh(doubleValue()));
	}

	@Override
	default public PsiReal psiTanh()
	{
		return new PsiReal(Math.tanh(doubleValue()));
	}

	@Override
	default public PsiReal psiAcos()
	{
		return new PsiReal(Math.acos(doubleValue()));
	}

	@Override
	default public PsiReal psiAsin()
	{
		return new PsiReal(Math.asin(doubleValue()));
	}

	@Override
	default public PsiReal psiAtan()
	{
		return new PsiReal(Math.atan(doubleValue()));
	}

	default public PsiReal psiHypot(final PsiRealNumeric oNumeric)
	{
		return new PsiReal(Math.hypot(doubleValue(), oNumeric.doubleValue()));
	}

	public PsiRealNumeric psiFloor();

	public PsiRealNumeric psiRound();

	public PsiRealNumeric psiCeiling();

	@Override
	default public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o instanceof PsiRealNumeric
				&& doubleValue()==((PsiRealNumeric)o).doubleValue());
	}

	/**
	*	“Less” arithmetic comparison.
	*/
	@Override
	default public PsiBoolean psiLt(final PsiRealNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()<oNumeric.doubleValue());
	}

	/**
	*	“Less or equal” arithmetic comparison.
	*/
	@Override
	default public PsiBoolean psiLe(final PsiRealNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()<=oNumeric.doubleValue());
	}

	/**
	*	“Greater” arithmetic comparison.
	*/
	@Override
	default public PsiBoolean psiGt(final PsiRealNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()>oNumeric.doubleValue());
	}

	/**
	*	“Greater or equal” arithmetic comparison.
	*/
	@Override
	default public PsiBoolean psiGe(final PsiRealNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()>=oNumeric.doubleValue());
	}
}
