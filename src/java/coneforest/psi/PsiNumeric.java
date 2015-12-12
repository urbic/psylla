package coneforest.psi;

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
	default public String getTypeName()
	{
		return "numeric";
	}

	int intValue();

	long longValue();

	double doubleValue();

	/**
	 *	@return this object.
	 */
	default public PsiNumeric psiRe()
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
	default public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiAdd((PsiNumeric)cn);
		return new PsiComplex(this).psiAdd(cn);
	}

	public PsiComplexNumeric psiSub(final PsiNumeric numeric);

	@Override
	default public PsiComplexNumeric psiSub(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiSub((PsiNumeric)cn);
		return new PsiComplex(this).psiSub(cn);
	}

	public PsiComplexNumeric psiMul(final PsiNumeric numeric);

	@Override
	default public PsiComplexNumeric psiMul(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiMul((PsiNumeric)cn);
		return new PsiComplex(this).psiMul(cn);
	}

	default public PsiReal psiDiv(final PsiNumeric numeric)
	{
		return new PsiReal(doubleValue()/numeric.doubleValue());
	}

	@Override
	default public PsiComplexNumeric psiDiv(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiDiv((PsiNumeric)cn);
		return new PsiComplex(this).psiDiv(cn);
	}

	@Override
	public PsiNumeric psiAbs();

	@Override
	default public PsiComplexNumeric psiSqrt()
	{
		if(doubleValue()>=0.D)
			return new PsiReal(Math.sqrt(doubleValue()));
		else
			return new PsiComplex(0, Math.sqrt(-doubleValue()));
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
	default public PsiReal psiAtan()
	{
		return new PsiReal(Math.atan(doubleValue()));
	}

	default public PsiReal psiHypot(PsiNumeric numeric)
	{
		return new PsiReal(Math.hypot(doubleValue(), numeric.doubleValue()));
	}

	public PsiNumeric psiFloor();

	public PsiNumeric psiRound();

	public PsiNumeric psiCeiling();

	@Override
	default public PsiBoolean psiEq(final PsiObject obj)
	{
		return PsiBoolean.valueOf(obj instanceof PsiNumeric
				&& doubleValue()==((PsiNumeric)obj).doubleValue());
	}

	/**
	 *	“Less” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiLt(final PsiNumeric numeric)
	{
		return PsiBoolean.valueOf(doubleValue()<numeric.doubleValue());
	}

	/**
	 *	“Less or equal” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiLe(final PsiNumeric numeric)
	{
		return PsiBoolean.valueOf(doubleValue()<=numeric.doubleValue());
	}

	/**
	 *	“Greater” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiGt(final PsiNumeric numeric)
	{
		return PsiBoolean.valueOf(doubleValue()>numeric.doubleValue());
	}

	/**
	 *	“Greater or equal” arithmetic comparison.
	 */
	@Override
	default public PsiBoolean psiGe(final PsiNumeric numeric)
	{
		return PsiBoolean.valueOf(doubleValue()>=numeric.doubleValue());
	}
}
