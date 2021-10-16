package coneforest.psylla.core;

@coneforest.psylla.Type("biginteger")
public class PsyBigInteger
	implements
		PsyBitwise<PsyIntegral>,
		PsyIntegral
{
	public PsyBigInteger(final java.math.BigInteger value)
	{
		this.value=value;
	}

	public PsyBigInteger(final long integer)
	{
		this.value=java.math.BigInteger.valueOf(integer);
	}

	public PsyBigInteger(final PsyInteger oInteger)
	{
		this(oInteger.longValue());
	}

	public PsyBigInteger(final String image)
	{
		this(new java.math.BigInteger(image));
	}

	public static PsyBigInteger parse(final String image)
	{
		return new PsyBigInteger(image);
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
	public java.math.BigInteger bigIntegerValue()
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
	public PsyBigInteger psyIdiv(final PsyIntegral oIntegral)
		throws PsyException
	{
		if(oIntegral instanceof PsyBigInteger)
			return new PsyBigInteger(value.divide(((PsyBigInteger)oIntegral).value));
		if(oIntegral instanceof PsyInteger)
			return new PsyBigInteger(
				value.divide(java.math.BigInteger.valueOf(((PsyInteger)oIntegral).longValue())));
		throw new PsyTypeCheckException();
	}

	@Override
	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyException
	{
		if(oIntegral instanceof PsyBigInteger)
			return PsyIntegral.valueOf(value.mod(((PsyBigInteger)oIntegral).value));
		if(oIntegral instanceof PsyInteger)
			//return new PsyBigInteger(
			//	value.mod(java.math.BigInteger.valueOf(((PsyInteger)oIntegral).longValue())));
			return PsyIntegral.valueOf(value.mod(((PsyInteger)oIntegral).bigIntegerValue()));
		throw new PsyTypeCheckException();
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.valueOf(value.multiply(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return PsyIntegral.valueOf(value.multiply(new PsyBigInteger((PsyInteger)oNumeric).value));
		return new PsyReal(doubleValue()*((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.valueOf(value.subtract(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return PsyIntegral.valueOf(value.subtract(new PsyBigInteger((PsyInteger)oNumeric).value));
		return new PsyReal(doubleValue()-((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.valueOf(value.add(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return PsyIntegral.valueOf(value.add(new PsyBigInteger((PsyInteger)oNumeric).value));
		return new PsyReal(doubleValue()+((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return PsyIntegral.valueOf(value.negate());
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.valueOf(value.equals(java.math.BigInteger.ZERO));
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
			return PsyInteger.valueOf(value.compareTo(java.math.BigInteger.valueOf(((PsyInteger)oNumeric).longValue())));
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

	private final java.math.BigInteger value;
}
