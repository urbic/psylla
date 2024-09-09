package coneforest.psylla.core;

import coneforest.psylla.runtime.*;

/**
*	The representation of {@code realnumeric}, an abstraction of real number.
*/
@Type("realnumeric")
public sealed interface PsyRealNumeric
	extends
		PsyNumeric,
		PsyConvertableToInteger,
		PsyConvertableToIntegral,
		PsyConvertableToRational,
		PsyConvertableToReal,
		PsyScalar<PsyRealNumeric>
	permits
		PsyRational,
		PsyReal
{

	public int intValue();

	public long longValue();

	public double doubleValue();

	public PsyRational rationalValue()
		throws PsyUndefinedResultException;

	@Override
	default public PsyRational psyToRational()
		throws PsyUndefinedResultException
	{
		return rationalValue();
	}

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

	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric);

	@Override
	default public PsyNumeric psyAdd(final PsyNumeric oNumeric)
	{
		return switch(oNumeric)
			{
				case PsyRealNumeric oRealNumeric->
					psyAdd(oRealNumeric);
				case PsyComplex oComplex->
					new PsyComplex(this).psyAdd(oComplex);
			};
	}

	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric);

	@Override
	default public PsyNumeric psySub(final PsyNumeric oNumeric)
	{
		return switch(oNumeric)
			{
				case PsyRealNumeric oRealNumeric->
					psySub(oRealNumeric);
				case PsyComplex oComplex->
					new PsyComplex(this).psySub(oComplex);
			};
	}

	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric);

	@Override
	default public PsyNumeric psyMul(final PsyNumeric oNumeric)
	{
		return switch(oNumeric)
			{
				case PsyRealNumeric oRealNumeric->
					psyMul(oRealNumeric);
				case PsyComplex oComplex->
					new PsyComplex(this).psyMul(oComplex);
			};
	}

	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException;

	@Override
	default public PsyNumeric psyDiv(final PsyNumeric oNumeric)
		throws PsyUndefinedResultException
	{
		return switch(oNumeric)
			{
				case PsyRealNumeric oRealNumeric->
					psyDiv(oRealNumeric);
				case PsyComplex oComplex->
					new PsyComplex(this).psyDiv(oComplex);
			};
	}

	default public PsyReal psyPow(final PsyRealNumeric oNumber)
	{
		return new PsyReal(Math.pow(doubleValue(), oNumber.doubleValue()));
	}

	@Override
	default public PsyNumeric psyPow(final PsyNumeric oNumeric)
	{
		return switch(oNumeric)
			{
				case PsyRealNumeric oRealNumeric->
					psyPow(oRealNumeric);
				case PsyComplex oComplex->
					// TODO
					new PsyComplex(this).psyPow(oComplex);
			};
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
		return PsyBoolean.of(o instanceof PsyRealNumeric oRealNumeric && compareTo(oRealNumeric)==0);
	}

	@Override
	public int compareTo(final PsyRealNumeric oRealNumeric);

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
