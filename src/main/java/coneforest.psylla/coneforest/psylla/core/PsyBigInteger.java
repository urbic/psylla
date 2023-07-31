package coneforest.psylla.core;

import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	The representation of {@code biginteger}.
*/
@Type("biginteger")
public final class PsyBigInteger
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
		if(oIntegral instanceof PsyBigInteger oBigInteger)
			return PsyIntegral.of(value.divide(oBigInteger.value));
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
			return PsyIntegral.of(value.mod(oIntegral.bigIntegerValue()));
		}
		catch(final ArithmeticException ex)
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
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyIntegral.of(value.multiply(oIntegral.bigIntegerValue()));
		if(oRealNumeric instanceof PsyRational oRational)
			return PsyRational.of((PsyIntegral)psyMul(oRational.psyNumerator()), oRational.psyDenominator());
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()*oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyRational.of(psyNumerator(), (PsyIntegral)psyDenominator().psyMul(oIntegral));
		if(oRealNumeric instanceof PsyRational oRational)
			return PsyRational.of(
					(PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()),
					(PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()*oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyIntegral.of(value.add(oIntegral.bigIntegerValue()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()+oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyIntegral.of(value.subtract(oIntegral.bigIntegerValue()));
		if(oRealNumeric instanceof PsyReal oReal)
			return new PsyReal(doubleValue()-oReal.doubleValue());
		throw new ClassCastException();
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
	public int cmp(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return value.compareTo(oIntegral.bigIntegerValue());
		if(oRealNumeric instanceof PsyRational oRational)
			return psyMul(oRational.psyDenominator()).cmp(oRational.psyNumerator());
		if(oRealNumeric instanceof PsyReal oReal)
			return Double.compare(doubleValue(), oReal.doubleValue());
		throw new ClassCastException();
	}

	@Override
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public boolean equals(final Object obj)
	{
		// TODO PsyInteger
		return obj instanceof PsyBigInteger oBigInteger
				&& value.equals(oBigInteger.value);
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	private final BigInteger value;
}
