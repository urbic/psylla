package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigInteger;

/**
*	The representation of {@code biginteger}, arbitrary precision integer.
*/
@Type("biginteger")
public final class PsyBigInteger
	implements PsyIntegral
{
	private final BigInteger value;

	/**
	*	Constructs a new {@code biginteger} for the given value.
	*
	*	@param value the value.
	*/
	public PsyBigInteger(final BigInteger value)
	{
		this.value=value;
	}

	@Override
	public boolean isZero()
	{
		return false;
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
		throws PsyRangeCheckException
	{
		try
		{
			return PsyIntegral.of(value.flipBit(oBit.intValue()));
		}
		catch(final ArithmeticException ex)
		{
			throw new PsyRangeCheckException();
		}
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
		return new PsyBigInteger(value.not());
	}

	@Override
	public PsyIntegral psyAbs()
	{
		return PsyIntegral.of(value.abs());
	}

	@Override
	public PsyIntegral psyIdiv(final PsyRational oRational)
		throws PsyUndefinedResultException
	{
		return switch(oRational)
			{
				case PsyInteger oInteger when oInteger==PsyInteger.ZERO->
					throw new PsyUndefinedResultException();
				case PsyInteger oInteger->PsyIntegral.of(
						value.divide(BigInteger.valueOf(oInteger.longValue())));
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(value.divide(oBigInteger.value));
				default->((PsyIntegral)psyMul(oRational.psyDenominator()))
						.psyIdiv(oRational.psyNumerator());
			};
	}

	@Override
	public PsyIntegral psyMod(final PsyRational oRational)
		throws PsyUndefinedResultException, PsyRangeCheckException
	{
		return switch(oRational)
			{
				case PsyIntegral oIntegral when oIntegral==PsyInteger.ZERO->
					throw new PsyUndefinedResultException();
				case PsyIntegral oIntegral->
					{
						try
						{
							yield PsyIntegral.of(value.mod(oIntegral.bigIntegerValue()));
						}
						catch(final ArithmeticException ex)
						{
							throw new PsyRangeCheckException();
						}
					}
				default->((PsyIntegral)psyMul(oRational.psyDenominator()))
						.psyMod(oRational.psyNumerator());
			};
	}

	@Override
	public PsyRational psyGCD(final PsyRational oRational)
	{
		return switch(oRational)
			{
				case PsyIntegral oIntegral when oIntegral==PsyInteger.ZERO->
					psyAbs();
				case PsyIntegral oIntegral->
					PsyIntegral.of(value.gcd(oIntegral.bigIntegerValue()));
				default->oRational.psyGCD(this);
			};
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					PsyIntegral.of(value.multiply(oIntegral.bigIntegerValue()));
				case PsyRational oRational->PsyRational.of((PsyIntegral)psyMul(oRational.psyNumerator()),
						oRational.psyDenominator());
				case PsyReal oReal->new PsyReal(doubleValue()*oReal.doubleValue());
			};
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					PsyRational.of(psyNumerator(), (PsyIntegral)psyDenominator().psyMul(oIntegral));
				case PsyRational oRational->PsyRational.of(
						(PsyIntegral)psyNumerator().psyMul(oRational.psyDenominator()),
						(PsyIntegral)psyDenominator().psyMul(oRational.psyNumerator()));
				case PsyReal oReal->new PsyReal(doubleValue()*oReal.doubleValue());
			};
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					PsyIntegral.of(value.add(oIntegral.bigIntegerValue()));
				case PsyRational oRational->
					PsyRational.of((PsyIntegral)psyMul(oRational.psyDenominator())
								.psyAdd(oRational.psyNumerator()),
							oRational.psyDenominator());
				case PsyReal oReal->new PsyReal(doubleValue()+oReal.doubleValue());
			};
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					PsyIntegral.of(value.subtract(oIntegral.bigIntegerValue()));
				case PsyRational oRational->
					PsyRational.of((PsyIntegral)psyMul(
							oRational.psyDenominator()).psySub(oRational.psyNumerator()),
						oRational.psyDenominator());
				case PsyReal oReal->new PsyReal(doubleValue()-oReal.doubleValue());
			};
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return PsyIntegral.of(value.negate());
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.of(value.signum());
	}

	@Override
	public int compareTo(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyIntegral oIntegral->
					value.compareTo(oIntegral.bigIntegerValue());
				case PsyRational oRational->
					psyMul(oRational.psyDenominator()).compareTo(oRational.psyNumerator());
				case PsyReal oReal->
					Double.compare(doubleValue(), oReal.doubleValue());
			};
	}

	@Override
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof PsyBigInteger oBigInteger
				&& value.equals(oBigInteger.value);
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}
}
