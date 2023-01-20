package coneforest.psylla.core;
import java.math.BigInteger;

@coneforest.psylla.Type("biginteger")
public class PsyBigInteger
	implements
		PsyIntegral
{
	public PsyBigInteger(final BigInteger value)
	{
		this.value=value;
	}

	public PsyBigInteger(final long integer)
	{
		this.value=BigInteger.valueOf(integer);
	}

	public PsyBigInteger(final PsyInteger oInteger)
	{
		this(oInteger.longValue());
	}

	public PsyBigInteger(final String image)
	{
		this(new BigInteger(image));
	}

	@Override
	public int intValue()
	{
		return value.intValue();
	}

	@Override
	public long longValue()
	{
		return value.longValue();
	}

	@Override
	public double doubleValue()
	{
		return value.doubleValue();
	}

	@Override
	public BigInteger bigIntegerValue()
	{
		return value;
	}

	@Override
	public PsyBigInteger psyBitShift(final PsyInteger oShift)
	{
		return new PsyBigInteger(value.shiftLeft(oShift.intValue()));
	}

	@Override
	public PsyBoolean psyTestBit(final PsyInteger oBit)
	{
		return PsyBoolean.of(value.testBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psySetBit(final PsyInteger oBit)
	{
		return PsyIntegral.of(value.setBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psyFlipBit(final PsyInteger oBit)
	{
		return PsyIntegral.of(value.flipBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psyClearBit(final PsyInteger oBit)
	{
		return PsyIntegral.of(value.clearBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psyOr(final PsyIntegral oIntegral)
	{
		return PsyIntegral.of(value.or(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyAnd(final PsyIntegral oIntegral)
	{
		return PsyIntegral.of(value.and(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyXor(final PsyIntegral oIntegral)
	{
		return PsyIntegral.of(value.xor(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyBigInteger psyNot()
	{
		// TODO
		return new PsyBigInteger(value.not());
	}

	@Override
	public PsyIntegral psyAbs()
	{
		return PsyIntegral.of(value.abs());
	}

	@Override
	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		if(oIntegral instanceof PsyBigInteger)
			return PsyIntegral.of(value.divide(((PsyBigInteger)oIntegral).value));
		else
			return PsyIntegral.of(
				value.divide(BigInteger.valueOf(((PsyInteger)oIntegral).longValue())));
	}

	@Override
	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException, PsyRangeCheckException
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		try
		{
			return PsyIntegral.of(value.mod(((PsyIntegral)oIntegral).bigIntegerValue()));
		}
		catch(final java.lang.ArithmeticException ex)
		{
			throw new PsyRangeCheckException();
		}
	}

	@Override
	public PsyIntegral psyGCD(final PsyIntegral oIntegral)
	{
		if(psyIsZero().booleanValue())
			return oIntegral;
		if(oIntegral.psyIsZero().booleanValue())
			return this;
		return PsyIntegral.of(value.gcd(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyIntegral.of(value.multiply(((PsyIntegral)oNumeric).bigIntegerValue()));
		if(oNumeric instanceof PsyRational)
			try
			{
				return PsyRational.of(
						(PsyIntegral)psyMul(((PsyRational)oNumeric).psyNumerator()),
						((PsyRational)oNumeric).psyDenominator()
					);
			}
			catch(final PsyUndefinedResultException e)
			{
				// NOP
			}
		return new PsyReal(doubleValue()*((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oNumeric)
		throws PsyUndefinedResultException
	{
		if(oNumeric instanceof PsyRational)
		{
			return PsyRational.of(
					(PsyIntegral)psyNumerator().psyMul(((PsyRational)oNumeric).psyDenominator()),
					(PsyIntegral)psyDenominator().psyMul(((PsyRational)oNumeric).psyNumerator()));
		}
		return new PsyReal(doubleValue()*((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyIntegral.of(value.add(((PsyIntegral)oNumeric).bigIntegerValue()));
		return new PsyReal(doubleValue()+((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyIntegral.of(value.subtract(((PsyIntegral)oNumeric).bigIntegerValue()));
		return new PsyReal(doubleValue()-((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return PsyIntegral.of(value.negate());
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.of(value.equals(BigInteger.ZERO));
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.of(value.signum());
	}

	@Override
	public PsyInteger psyCmp(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return PsyInteger.of(value.compareTo(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return PsyInteger.of(value.compareTo(BigInteger.valueOf(((PsyInteger)oNumeric).longValue())));
		return PsyInteger.of(Double.valueOf(doubleValue()).compareTo(((PsyReal)oNumeric).doubleValue()));
	}

	@Override
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public boolean equals(final Object object)
	{
		// TODO PsyInteger
		return object instanceof PsyBigInteger
				&& value.equals(((PsyBigInteger)object).value);
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	private final BigInteger value;
}
