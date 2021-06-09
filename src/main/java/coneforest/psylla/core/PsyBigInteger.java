package coneforest.psylla.core;

@coneforest.psylla.Type("biginteger")
public class PsyBigInteger
	implements
		PsyBitwise<PsyBigInteger>,
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
	public PsyBigInteger psySetBit(final PsyInteger oBit)
	{
		return new PsyBigInteger(value.setBit(oBit.intValue()));
	}

	@Override
	public PsyBigInteger psyFlipBit(final PsyInteger oBit)
	{
		return new PsyBigInteger(value.flipBit(oBit.intValue()));
	}

	@Override
	public PsyBigInteger psyClearBit(final PsyInteger oBit)
	{
		return new PsyBigInteger(value.clearBit(oBit.intValue()));
	}

	@Override
	public PsyBigInteger psyOr(final PsyBigInteger oBigInteger)
	{
		return new PsyBigInteger(value.or(oBigInteger.value));
	}

	@Override
	public PsyBigInteger psyAnd(final PsyBigInteger oBigInteger)
	{
		return new PsyBigInteger(value.and(oBigInteger.value));
	}

	@Override
	public PsyBigInteger psyXor(final PsyBigInteger oBigInteger)
	{
		return new PsyBigInteger(value.xor(oBigInteger.value));
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
	public PsyBigInteger psyAbs()
	{
		return new PsyBigInteger(value.abs());
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
	public PsyBigInteger psyMod(final PsyIntegral oIntegral)
		throws PsyException
	{
		if(oIntegral instanceof PsyBigInteger)
			return new PsyBigInteger(value.mod(((PsyBigInteger)oIntegral).value));
		if(oIntegral instanceof PsyInteger)
			return new PsyBigInteger(
				value.mod(java.math.BigInteger.valueOf(((PsyInteger)oIntegral).longValue())));
		throw new PsyTypeCheckException();
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return new PsyBigInteger(value.multiply(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return new PsyBigInteger(value.multiply(new PsyBigInteger((PsyInteger)oNumeric).value));
		return new PsyReal(doubleValue()*((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return new PsyBigInteger(value.subtract(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return new PsyBigInteger(value.subtract(new PsyBigInteger((PsyInteger)oNumeric).value));
		return new PsyReal(doubleValue()-((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyBigInteger)
			return new PsyBigInteger(value.add(((PsyBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsyInteger)
			return new PsyBigInteger(value.add(new PsyBigInteger((PsyInteger)oNumeric).value));
		return new PsyReal(doubleValue()+((PsyReal)oNumeric).doubleValue());
	}

	@Override
	public PsyBigInteger psyNeg()
	{
		return new PsyBigInteger(value.negate());
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.valueOf(value.equals(java.math.BigInteger.ZERO));
	}

	@Override
	public PsyBigInteger psySignum()
	{
		return new PsyBigInteger(value.signum());
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
	public boolean equals(final Object object)
	{
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
