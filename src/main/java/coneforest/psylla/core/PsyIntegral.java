package coneforest.psylla.core;
import coneforest.psylla.*;

/**
*	A representation of {@code integral} object.
*/
@Type("integral")
public interface PsyIntegral
	extends
		PsyBitwise<PsyIntegral>,
		PsyRealNumeric
{
	public java.math.BigInteger bigIntegerValue();

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
		throws PsyErrorException;

	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyErrorException;

	public PsyIntegral psyGCD(final PsyIntegral oIntegral)
		throws PsyErrorException;

	default PsyIntegral psyLCM(final PsyIntegral oIntegral)
		throws PsyErrorException
	{
		if(psyIsZero().booleanValue() || oIntegral.psyIsZero().booleanValue())
			return PsyInteger.ZERO;
		return ((PsyIntegral)psyMul(oIntegral)).psyIdiv(psyGCD(oIntegral));
	}

	public static PsyInteger valueOf(final long longValue)
	{
		return PsyInteger.valueOf(longValue);
	}

	default public PsyIntegral psyToIntegral()
	{
		return this;
	}

	public static PsyIntegral valueOf(final java.math.BigInteger bigIntegerValue)
	{
		try
		{
			return PsyInteger.valueOf(bigIntegerValue.longValueExact());
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
				return PsyInteger.valueOf(Long.parseLong(image));
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
