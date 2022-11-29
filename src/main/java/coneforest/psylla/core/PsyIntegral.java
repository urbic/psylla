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

	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyErrorException;

	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyErrorException;

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
		};

}
