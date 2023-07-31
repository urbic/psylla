package coneforest.psylla.core;

import coneforest.psylla.*;

/**
*	The representation of {@code realnumeric}, an abstraction of real number.
*/
@Type("realnumeric")
public sealed interface PsyRealNumeric
	extends
		PsyNumeric,
		PsyConvertableToInteger,
		PsyConvertableToIntegral,
		PsyConvertableToReal,
		PsyScalar<PsyRealNumeric>
	permits
		PsyRational,
		PsyReal
{

	public int intValue();

	public long longValue();

	public double doubleValue();

	@Override
	default public double realValue()
	{
		return doubleValue();
	}

	@Override
	default public double imagValue()
	{
		return 0.D;
	}

	@Override
	default public PsyInteger psyToInteger()
		throws PsyRangeCheckException
	{
		if(doubleValue()>=Long.MIN_VALUE
				&& doubleValue()<=Long.MAX_VALUE)
			return PsyInteger.of(longValue());
		else
			throw new PsyRangeCheckException();
	}

	@Override
	default public PsyReal psyToReal()
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
		if(oNumber instanceof PsyComplex oComplex)
			return new PsyComplex(this).psyAdd(oComplex);
		throw new ClassCastException();
	}

	public PsyRealNumeric psySub(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psySub(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psySub(oRealNumeric);
		if(oNumber instanceof PsyComplex oComplex)
			return new PsyComplex(this).psySub(oComplex);
		throw new ClassCastException();
	}

	public PsyRealNumeric psyMul(final PsyRealNumeric oNumber);

	@Override
	default public PsyNumeric psyMul(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psyMul(oRealNumeric);
		if(oNumber instanceof PsyComplex oComplex)
			return new PsyComplex(this).psyMul(oComplex);
		throw new ClassCastException();
	}

	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException;

	@Override
	default public PsyNumeric psyDiv(final PsyNumeric oNumeric)
		throws PsyUndefinedResultException
	{
		if(oNumeric instanceof PsyRealNumeric oRealNumeric)
			return psyDiv(oRealNumeric);
		if(oNumeric instanceof PsyComplex oComplex)
			return new PsyComplex(this).psyDiv(oComplex);
		throw new ClassCastException();
	}

	default public PsyReal psyPow(final PsyRealNumeric oNumber)
	{
		return new PsyReal(Math.pow(doubleValue(), oNumber.doubleValue()));
	}

	@Override
	default public PsyNumeric psyPow(final PsyNumeric oNumber)
	{
		if(oNumber instanceof PsyRealNumeric oRealNumeric)
			return psyPow(oRealNumeric);
		if(oNumber instanceof PsyComplex oComplex)
			// TODO
			return new PsyComplex(this).psyPow(oComplex);
		throw new ClassCastException();
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
		return PsyBoolean.of(o instanceof PsyRealNumeric oRealNumeric && cmp(oRealNumeric)==0);
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

	/**
	*	Context action of the {@code ceiling} operator.
	*/
	@OperatorType("ceiling")
	public static final ContextAction PSY_CEILING
		=ContextAction.<PsyRealNumeric>ofFunction(PsyRealNumeric::psyCeiling);

	/**
	*	Context action of the {@code floor} operator.
	*/
	@OperatorType("floor")
	public static final ContextAction PSY_FLOOR
		=ContextAction.<PsyRealNumeric>ofFunction(PsyRealNumeric::psyFloor);

	/**
	*	Context action of the {@code hypot} operator.
	*/
	@OperatorType("hypot")
	public static final ContextAction PSY_HYPOT
		=ContextAction.<PsyRealNumeric, PsyRealNumeric>ofBiFunction(PsyRealNumeric::psyHypot);

	/**
	*	Context action of the {@code round} operator.
	*/
	@OperatorType("round")
	public static final ContextAction PSY_ROUND
		=ContextAction.<PsyRealNumeric>ofFunction(PsyRealNumeric::psyRound);
}
