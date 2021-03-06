package coneforest.psylla.core;

/**
*	A representation of Ψ-{@code realnumeric}, an abstraction of real numbers.
*/
@coneforest.psylla.Type("realnumeric")
public interface PsyRealNumeric
	extends
		PsyNumeric,
		PsyConvertableToInteger,
		PsyConvertableToReal,
		PsyScalar<PsyRealNumeric>
{

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

	/**
	*	Returns the signum of this object.
	*
	*	@return the Ψ-{@code realnumeric} signum of this object.
	*/
	public PsyRealNumeric psySignum();

	@Override
	default public PsyInteger psyToInteger()
		throws PsyException
	{
		if(doubleValue()>=Long.MIN_VALUE
				&& doubleValue()<=Long.MAX_VALUE)
			return PsyInteger.valueOf(longValue());
		else
			throw new PsyRangeCheckException();
	}

	@Override
	default public PsyReal psyToReal()
		throws PsyException
	{
		return new PsyReal(doubleValue());
	}

	@Override
	public PsyRealNumeric psyNeg();

	public PsyNumeric psyAdd(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psyAdd(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric)
			return psyAdd((PsyRealNumeric)oNumber);
		return new PsyComplex(this).psyAdd(oNumber);
	}

	public PsyNumeric psySub(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psySub(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric)
			return psySub((PsyRealNumeric)oNumber);
		return new PsyComplex(this).psySub(oNumber);
	}

	public PsyNumeric psyMul(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psyMul(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric)
			return psyMul((PsyRealNumeric)oNumber);
		return new PsyComplex(this).psyMul(oNumber);
	}

	default public PsyReal psyDiv(final PsyRealNumeric oNumber)
	{
		return new PsyReal(doubleValue()/oNumber.doubleValue());
	}

	@Override
	default public PsyNumeric psyDiv(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric)
			return psyDiv((PsyRealNumeric)oNumber);
		return new PsyComplex(this).psyDiv(oNumber);
	}

	default public PsyReal psyPow(final PsyRealNumeric oNumber)
	{
		return new PsyReal(Math.pow(doubleValue(), oNumber.doubleValue()));
	}

	@Override
	default public PsyNumeric psyPow(final PsyNumeric oNumber)
		throws PsyException
	{
		if(oNumber instanceof PsyRealNumeric)
			return psyPow((PsyRealNumeric)oNumber);
		return new PsyComplex(this).psyPow(oNumber);
	}

	@Override
	public PsyRealNumeric psyAbs();

	@Override
	default public PsyReal psySqrt()
	{
		return new PsyReal(Math.sqrt(doubleValue()));
	}

	@Override
	default public PsyReal psyCbrt()
	{
		return new PsyReal(Math.cbrt(doubleValue()));
	}

	@Override
	default public PsyReal psyExp()
	{
		return new PsyReal(Math.exp(doubleValue()));
	}

	@Override
	default public PsyReal psyLog()
	{
		return new PsyReal(Math.log(doubleValue()));
	}

	@Override
	default public PsyReal psyCos()
	{
		return new PsyReal(Math.cos(doubleValue()));
	}

	@Override
	default public PsyReal psySin()
	{
		return new PsyReal(Math.sin(doubleValue()));
	}

	@Override
	default public PsyReal psyTan()
	{
		return new PsyReal(Math.tan(doubleValue()));
	}

	@Override
	default public PsyReal psyCosh()
	{
		return new PsyReal(Math.cosh(doubleValue()));
	}

	@Override
	default public PsyReal psySinh()
	{
		return new PsyReal(Math.sinh(doubleValue()));
	}

	@Override
	default public PsyReal psyTanh()
	{
		return new PsyReal(Math.tanh(doubleValue()));
	}

	@Override
	default public PsyReal psyAcos()
	{
		return new PsyReal(Math.acos(doubleValue()));
	}

	@Override
	default public PsyReal psyAsin()
	{
		return new PsyReal(Math.asin(doubleValue()));
	}

	@Override
	default public PsyReal psyAtan()
	{
		return new PsyReal(Math.atan(doubleValue()));
	}

	default public PsyReal psyHypot(final PsyRealNumeric oNumeric)
	{
		return new PsyReal(Math.hypot(doubleValue(), oNumeric.doubleValue()));
	}

	public PsyRealNumeric psyFloor();

	public PsyRealNumeric psyRound();

	public PsyRealNumeric psyCeiling();

	@Override
	default public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.valueOf(o instanceof PsyRealNumeric
				&& doubleValue()==((PsyRealNumeric)o).doubleValue());
	}

	/**
	*	“Less” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyLt(final PsyRealNumeric oNumeric)
	{
		return PsyBoolean.valueOf(doubleValue()<oNumeric.doubleValue());
	}

	/**
	*	“Less or equal” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyLe(final PsyRealNumeric oNumeric)
	{
		return PsyBoolean.valueOf(doubleValue()<=oNumeric.doubleValue());
	}

	/**
	*	“Greater” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyGt(final PsyRealNumeric oNumeric)
	{
		return PsyBoolean.valueOf(doubleValue()>oNumeric.doubleValue());
	}

	/**
	*	“Greater or equal” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyGe(final PsyRealNumeric oNumeric)
	{
		return PsyBoolean.valueOf(doubleValue()>=oNumeric.doubleValue());
	}
}
