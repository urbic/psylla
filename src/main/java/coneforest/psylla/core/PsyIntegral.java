package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code integral} object.
*/
@Type("integral")
public interface PsyIntegral
	extends
		PsyBitwise<PsyIntegral>,
		PsyRational
{
	@Override
	default public PsyIntegral psyNumerator()
	{
		return this;
	}

	@Override
	default public PsyIntegral psyDenominator()
	{
		return ONE;
	}

	@Override
	public PsyIntegral psyAbs();

	@Override
	public PsyIntegral psyNeg();

	@Override
	default public PsyIntegral psyFloor()
	{
		return this;
	}

	@Override
	default public PsyIntegral psyCeiling()
	{
		return this;
	}

	@Override
	default public PsyIntegral psyRound()
	{
		return this;
	}

	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException;

	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException, PsyRangeCheckException;

	public PsyIntegral psyGCD(final PsyIntegral oIntegral);

	default PsyIntegral psyLCM(final PsyIntegral oIntegral)
		throws PsyErrorException
	{
		if(psyIsZero().booleanValue() || oIntegral.psyIsZero().booleanValue())
			return PsyInteger.ZERO;
		return ((PsyIntegral)psyMul(oIntegral)).psyIdiv(psyGCD(oIntegral));
	}

	default public PsyIntegral psyToIntegral()
	{
		return this;
	}

	public static PsyInteger of(final long longValue)
	{
		return PsyInteger.of(longValue);
	}

	public static PsyIntegral of(final java.math.BigInteger bigIntegerValue)
	{
		try
		{
			return PsyInteger.of(bigIntegerValue.longValueExact());
		}
		catch(final ArithmeticException ex)
		{
			return new PsyBigInteger(bigIntegerValue);
		}
	}

	public static PsyIntegral parse(final String image)
		throws PsySyntaxErrorException
	{
		try
		{
			try
			{
				return PsyInteger.of(Long.parseLong(image));
			}
			catch(final NumberFormatException ex)
			{
				return new PsyBigInteger(image);
			}
		}
		catch(final NumberFormatException ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	public static final PsyInteger ZERO=PsyInteger.ZERO;
	public static final PsyInteger ONE=PsyInteger.ONE;
	public static final PsyInteger TWO=PsyInteger.TWO;
	public static final PsyInteger MINUS_ONE=PsyInteger.MINUS_ONE;

	public static final PsyOperator[] OPERATORS=
		{
			new PsyOperator.Arity21<PsyIntegral, PsyIntegral>
				("idiv", PsyIntegral::psyIdiv),
			new PsyOperator.Arity21<PsyIntegral, PsyIntegral>
				("mod", PsyIntegral::psyMod),
			new PsyOperator.Arity21<PsyIntegral, PsyIntegral>
				("gcd", PsyIntegral::psyGCD),
			new PsyOperator.Arity21<PsyIntegral, PsyIntegral>
				("lcm", PsyIntegral::psyLCM),
		};

}
