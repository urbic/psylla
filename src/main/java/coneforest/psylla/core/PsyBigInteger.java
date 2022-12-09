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
		return PsyBoolean.valueOf(value.testBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psySetBit(final PsyInteger oBit)
	{
		return PsyIntegral.valueOf(value.setBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psyFlipBit(final PsyInteger oBit)
	{
		return PsyIntegral.valueOf(value.flipBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psyClearBit(final PsyInteger oBit)
	{
		return PsyIntegral.valueOf(value.clearBit(oBit.intValue()));
	}

	@Override
	public PsyIntegral psyOr(final PsyIntegral oIntegral)
	{
		return PsyIntegral.valueOf(value.or(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyAnd(final PsyIntegral oIntegral)
	{
		return PsyIntegral.valueOf(value.and(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyXor(final PsyIntegral oIntegral)
	{
		return PsyIntegral.valueOf(value.xor(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyBigInteger psyNot()
	{
		// TODO
		return new PsyBigInteger(value.not());
	}

	@Override
	public PsyBigInteger psyCeiling()
	{
		return this;
	}

	@Override
	public PsyBigInteger psyFloor()
	{
		return this;
	}

	@Override
	public PsyBigInteger psyRound()
	{
		return this;
	}

	@Override
	public PsyIntegral psyAbs()
	{
		return PsyIntegral.valueOf(value.abs());
	}

	@Override
	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyErrorException
	{
		if(oIntegral instanceof PsyBigInteger)
			return PsyIntegral.valueOf(value.divide(((PsyBigInteger)oIntegral).value));
		if(oIntegral instanceof PsyInteger)
			return PsyIntegral.valueOf(
				value.divide(BigInteger.valueOf(((PsyInteger)oIntegral).longValue())));
		throw new PsyTypeCheckException();
	}

	@Override
	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyErrorException
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		try
		{
			if(oIntegral instanceof PsyIntegral)
				return PsyIntegral.valueOf(value.mod(((PsyIntegral)oIntegral).bigIntegerValue()));
		}
		catch(final java.lang.ArithmeticException ex)
		{
			throw new PsyRangeCheckException();
		}
		throw new PsyTypeCheckException();
	}

	@Override
	public PsyIntegral psyGCD(final PsyIntegral oIntegral)
		throws PsyErrorException
	{
		if(psyIsZero().booleanValue())
			return oIntegral;
		if(oIntegral.psyIsZero().booleanValue())
			return this;
		if(value.compareTo(BigInteger.ZERO)<0 || oIntegral.psyCmp(ZERO).intValue()<0)
			throw new PsyRangeCheckException();
		return PsyIntegral.valueOf(value.gcd(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyIntegral.valueOf(value.multiply(((PsyIntegral)oNumeric).bigIntegerValue()));
		return new PsyReal(doubleValue()*((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyIntegral.valueOf(value.add(((PsyIntegral)oNumeric).bigIntegerValue()));
		return new PsyReal(doubleValue()+((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyIntegral.valueOf(value.subtract(((PsyIntegral)oNumeric).bigIntegerValue()));
		return new PsyReal(doubleValue()-((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return PsyIntegral.valueOf(value.negate());
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.valueOf(value.equals(BigInteger.ZERO));
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.valueOf(value.signum());
	}

	@Override
	public PsyInteger psyCmp(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return PsyInteger.valueOf(value.compareTo(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return PsyInteger.valueOf(value.compareTo(BigInteger.valueOf(((PsyInteger)oNumeric).longValue())));
		return PsyInteger.valueOf(Double.valueOf(doubleValue()).compareTo(((PsyReal)oNumeric).doubleValue()));
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
