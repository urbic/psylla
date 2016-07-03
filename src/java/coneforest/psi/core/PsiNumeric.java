package coneforest.psi.core;

/**
*	A representation of Ψ-{@code numeric}, an abstraction of real numbers.
*/
public interface PsiNumeric
	extends
		PsiComplexNumeric,
		PsiConvertableToInteger,
		PsiConvertableToReal,
		PsiScalar<PsiNumeric>
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

	/**
	 *	@return this object.
	 */
	default public PsiNumeric psiRealPart()
	{
		return this;
	}

	/**
	 *	@return this object.
	 */
	@Override
	default PsiNumeric psiConjugate()
	{
		return this;
	}

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
	default public PsiReal psiArg()
		throws PsiUndefinedResultException
	{
		if(doubleValue()>0)
			return PsiReal.ZERO;
		else if(doubleValue()<0)
			return PsiReal.PI;
		else
			throw new PsiUndefinedResultException();
	}

	@Override
	public PsiNumeric psiNeg();

	public PsiComplexNumeric psiAdd(final PsiNumeric numeric);

	@Override
	default public PsiComplexNumeric psiAdd(final PsiComplexNumeric oNumber)
	{
		if(oNumber instanceof PsiNumeric)
			return psiAdd((PsiNumeric)oNumber);
		return new PsiComplex(this).psiAdd(oNumber);
	}

	public PsiComplexNumeric psiSub(final PsiNumeric oNumeric);

	@Override
	default public PsiComplexNumeric psiSub(final PsiComplexNumeric oNumber)
	{
		if(oNumber instanceof PsiNumeric)
			return psiSub((PsiNumeric)oNumber);
		return new PsiComplex(this).psiSub(oNumber);
	}

	public PsiComplexNumeric psiMul(final PsiNumeric oNumeric);

	@Override
	default public PsiComplexNumeric psiMul(final PsiComplexNumeric oNumber)
	{
		if(oNumber instanceof PsiNumeric)
			return psiMul((PsiNumeric)oNumber);
		return new PsiComplex(this).psiMul(oNumber);
	}

	default public PsiReal psiDiv(final PsiNumeric oNumeric)
	{
		return new PsiReal(doubleValue()/oNumeric.doubleValue());
	}

	@Override
	default public PsiComplexNumeric psiDiv(final PsiComplexNumeric oNumber)
	{
		if(oNumber instanceof PsiNumeric)
			return psiDiv((PsiNumeric)oNumber);
		return new PsiComplex(this).psiDiv(oNumber);
	}

	@Override
	public PsiNumeric psiAbs();

	@Override
	default public PsiComplexNumeric psiSqrt()
	{
		if(doubleValue()>=0.D)
			return new PsiReal(Math.sqrt(doubleValue()));
		else
			return new PsiComplex(0.D, Math.sqrt(-doubleValue()));
	}

	@Override
	default public PsiComplexNumeric psiCbrt()
	{
		return new PsiReal(Math.cbrt(doubleValue()));
	}

	@Override
	default public PsiReal psiExp()
	{
		return new PsiReal(Math.exp(doubleValue()));
	}

	@Override
	default public PsiComplexNumeric psiLog()
		throws PsiUndefinedResultException
	{
		if(doubleValue()>0.D)
			return new PsiReal(Math.log(doubleValue()));
		else if(doubleValue()<0.D)
			return new PsiComplex((PsiNumeric)psiAbs().psiLog(), new PsiReal(Math.PI));
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

	default public PsiReal psiHypot(PsiNumeric oNumeric)
	{
		return new PsiReal(Math.hypot(doubleValue(), oNumeric.doubleValue()));
	}

	public PsiNumeric psiFloor();

	public PsiNumeric psiRound();

	public PsiNumeric psiCeiling();

	@Override
	default public PsiBoolean psiEq(final PsiObject o)
	{
		return PsiBoolean.valueOf(o instanceof PsiNumeric
				&& doubleValue()==((PsiNumeric)o).doubleValue());
	}

	/**
	 *	“Less” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiLt(final PsiNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()<oNumeric.doubleValue());
	}

	/**
	 *	“Less or equal” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiLe(final PsiNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()<=oNumeric.doubleValue());
	}

	/**
	 *	“Greater” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiGt(final PsiNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()>oNumeric.doubleValue());
	}

	/**
	 *	“Greater or equal” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiGe(final PsiNumeric oNumeric)
	{
		return PsiBoolean.valueOf(doubleValue()>=oNumeric.doubleValue());
	}
}
