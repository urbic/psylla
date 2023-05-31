package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code realnumeric}, an abstraction of real numbers.
*/
@Type("realnumeric")
public interface PsyRealNumeric
	extends
		PsyNumeric,
		PsyConvertableToInteger,
		PsyConvertableToIntegral,
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

	@Override
	default public PsyInteger psyToInteger()
		throws PsyErrorException
	{
		if(doubleValue()>=Long.MIN_VALUE
				&& doubleValue()<=Long.MAX_VALUE)
			return PsyInteger.of(longValue());
		else
			throw new PsyRangeCheckException();
	}

	@Override
	default public PsyReal psyToReal()
		throws PsyErrorException
	{
		return new PsyReal(doubleValue());
	}

	@Override
	public PsyRealNumeric psyNeg();

	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psyAdd(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psyAdd(oRealNumeric);
		return new PsyComplex(this).psyAdd(oNumber);
	}

	public PsyRealNumeric psySub(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psySub(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psySub(oRealNumeric);
		return new PsyComplex(this).psySub(oNumber);
	}

	public PsyRealNumeric psyMul(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psyMul(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psyMul(oRealNumeric);
		return new PsyComplex(this).psyMul(oNumber);
	}

	public PsyRealNumeric psyDiv(final PsyRealNumeric oNumber)
		throws PsyUndefinedResultException;

	@Override
	default public PsyNumeric psyDiv(final PsyNumeric oNumber)
		throws PsyUndefinedResultException
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psyDiv(oRealNumeric);
		return new PsyComplex(this).psyDiv(oNumber);
	}

	default public PsyReal psyPow(final PsyRealNumeric oNumber)
	{
		return new PsyReal(Math.pow(doubleValue(), oNumber.doubleValue()));
	}

	@Override
	default public PsyNumeric psyPow(final PsyNumeric oNumber)
		throws PsyErrorException
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psyPow(oRealNumeric);
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

	default public PsyReal psyHypot(final PsyRealNumeric oRealNumeric)
	{
		return new PsyReal(Math.hypot(doubleValue(), oRealNumeric.doubleValue()));
	}

	public PsyRealNumeric psyFloor();

	public PsyRealNumeric psyCeiling();

	public PsyRealNumeric psyRound();

	@Override
	default public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(o instanceof PsyRealNumeric oRealNumeric
				&& cmp(oRealNumeric)==0);
	}

	public int cmp(final PsyRealNumeric oRealNumeric);

	/**
	*	The “less” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyLt(final PsyRealNumeric oRealNumeric)
	{
		return PsyBoolean.of(cmp(oRealNumeric)<0);
	}

	/**
	*	The “less or equal” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyLe(final PsyRealNumeric oRealNumeric)
	{
		return PsyBoolean.of(cmp(oRealNumeric)<=0);
	}

	/**
	*	The “greater” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyGt(final PsyRealNumeric oRealNumeric)
	{
		return PsyBoolean.of(cmp(oRealNumeric)>0);
	}

	/**
	*	The “greater or equal” arithmetic comparison.
	*/
	@Override
	default public PsyBoolean psyGe(final PsyRealNumeric oRealNumeric)
	{
		return PsyBoolean.of(cmp(oRealNumeric)>=0);
	}

	@Override
	default public PsyInteger psyCmp(final PsyRealNumeric oRealNumeric)
	{
		return PsyInteger.of(cmp(oRealNumeric));
	}

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity11<PsyRealNumeric>
				("ceiling", PsyRealNumeric::psyCeiling),
			new PsyOperator.Arity11<PsyRealNumeric>
				("floor", PsyRealNumeric::psyFloor),
			new PsyOperator.Arity21<PsyRealNumeric, PsyRealNumeric>
				("hypot", PsyRealNumeric::psyHypot),
			new PsyOperator.Arity11<PsyRealNumeric>
				("round", PsyRealNumeric::psyRound),
		};
}
