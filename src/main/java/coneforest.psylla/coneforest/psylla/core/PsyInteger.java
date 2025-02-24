package coneforest.psylla.core;

import coneforest.psylla.runtime.*;
import java.math.BigInteger;

/**
*	The representation of {@code integer}.
*/
@Type("integer")
public final class PsyInteger
	implements
		PsyBitwise<PsyIntegral>,
		PsyIntegral
{
	/**
	*	An {@code integer} representing the number 0.
	*/
	public static final PsyInteger ZERO=PsyInteger.of(0L);

	/**
	*	An {@code integer} representing the number 1.
	*/
	public static final PsyInteger ONE=PsyInteger.of(1L);

	/**
	*	An {@code integer} representing the number 2.
	*/
	public static final PsyInteger TWO=PsyInteger.of(2L);

	/**
	*	An {@code integer} representing the number −1.
	*/
	public static final PsyInteger MINUS_ONE=PsyInteger.of(-1L);

	/**
	*	An {@code integer} representing the maximum representable value.
	*/
	public static final PsyInteger MAX_VALUE=PsyInteger.of(Long.MAX_VALUE);

	/**
	*	An {@code integer} representing the minimum representable value.
	*/
	public static final PsyInteger MIN_VALUE=PsyInteger.of(Long.MIN_VALUE);

	private final long value;

	private PsyInteger(final long value)
	{
		this.value=value;
	}

	@Override
	public boolean isZero()
	{
		return this==ZERO;
	}

	@Override
	public int intValue()
	{
		return (int)value;
	}

	@Override
	public long longValue()
	{
		return value;
	}

	@Override
	public double doubleValue()
	{
		return value;
	}

	@Override
	public BigInteger bigIntegerValue()
	{
		return BigInteger.valueOf(value);
	}

	@Override
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public PsyInteger psyNot()
	{
		return PsyInteger.of(~value);
	}

	@Override
	public PsyIntegral psyOr(final PsyIntegral oIntegral)
	{
		return switch(oIntegral)
			{
				case PsyInteger oInteger->
					of(value|oInteger.value);
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(bigIntegerValue().or(oBigInteger.bigIntegerValue()));
			};
	}

	@Override
	public PsyIntegral psyAnd(final PsyIntegral oIntegral)
	{
		return switch(oIntegral)
			{
				case PsyInteger oInteger->
					of(value&oInteger.value);
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(bigIntegerValue().and(oBigInteger.bigIntegerValue()));
			};
	}

	@Override
	public PsyIntegral psyXor(final PsyIntegral oIntegral)
	{
		return switch(oIntegral)
			{
				case PsyInteger oInteger->
					of(value^oInteger.value);
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(bigIntegerValue().xor(oBigInteger.bigIntegerValue()));
			};
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return value!=Long.MIN_VALUE?
			PsyInteger.of(-value): new PsyBigInteger(bigIntegerValue().negate());
	}

	@Override
	public PsyIntegral psyAbs()
	{
		return value>0L? this: psyNeg();
	}

	@Override
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyBoolean.of((value&(1L<<bit))!=0);
	}

	@Override
	public PsyInteger psyClearBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.of(value&~(1L<<bit));
	}

	@Override
	public PsyInteger psySetBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.of(value|(1L<<bit));
	}

	@Override
	public PsyInteger psyFlipBit(final PsyInteger oBit)
		throws PsyRangeCheckException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.of(value^(1L<<bit));
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.of(Long.signum(value));
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyInteger oInteger->
					{
						try
						{
							yield PsyInteger.of(Math.addExact(value, oInteger.value));
						}
						catch(final ArithmeticException ex)
						{
							yield PsyIntegral.of(bigIntegerValue().add(oInteger.bigIntegerValue()));
						}
					}
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(
						bigIntegerValue().add(oBigInteger.bigIntegerValue()));
				case PsyRational oRational->
					PsyRational.of(
						(PsyIntegral)psyMul(oRational.psyDenominator()).psyAdd(oRational.psyNumerator()),
						oRational.psyDenominator());
				case PsyReal oReal->
					new PsyReal(value+oReal.doubleValue());
			};
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyInteger oInteger->
					{
						try
						{
							yield PsyInteger.of(Math.subtractExact(value, oInteger.value));
						}
						catch(final ArithmeticException ex)
						{
							yield PsyIntegral.of(bigIntegerValue().subtract(oInteger.bigIntegerValue()));
						}
					}
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(bigIntegerValue().subtract(oBigInteger.bigIntegerValue()));
				case PsyRational oRational->PsyRational.of(
						(PsyIntegral)psyMul(oRational.psyDenominator()).psySub(oRational.psyNumerator()),
						oRational.psyDenominator());
				case PsyReal oReal->new PsyReal(value-oReal.doubleValue());
			};
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyInteger oInteger->
					{
						try
						{
							yield of(Math.multiplyExact(value, oInteger.value));
						}
						catch(final ArithmeticException ex)
						{
							yield PsyIntegral.of(bigIntegerValue().multiply(oInteger.bigIntegerValue()));
						}
					}
				case PsyBigInteger oBigInteger->
					PsyIntegral.of(bigIntegerValue().multiply(oBigInteger.bigIntegerValue()));
				case PsyRational oRational->PsyRational.of(
						(PsyIntegral)psyMul(oRational.psyNumerator()), oRational.psyDenominator());
				case PsyReal oReal->new PsyReal(value*oReal.doubleValue());
			};
	}

	@Override
	public int compareTo(final PsyRealNumeric oRealNumeric)
	{
		return switch(oRealNumeric)
			{
				case PsyInteger oInteger->
					Long.compare(value, oInteger.value);
				case PsyBigInteger oBigInteger->
					bigIntegerValue().compareTo(oBigInteger.bigIntegerValue());
				case PsyRational oRational->
					psyMul(oRational.psyDenominator()).compareTo(oRational.psyNumerator());
				case PsyReal oReal->
					Double.compare(doubleValue(), oReal.doubleValue());
			};
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResultException
	{
		if(oRealNumeric==ZERO)
			throw new PsyUndefinedResultException();
		return switch(oRealNumeric)
			{
				case PsyInteger oInteger->
					PsyFraction.of(value, oInteger.value);
				case PsyBigInteger oBigInteger->
					PsyRational.of(this, oBigInteger);
				case PsyRational oRational->
					PsyRational.of((PsyIntegral)psyMul(oRational.psyDenominator()),
						oRational.psyNumerator());
				case PsyReal oReal->
					new PsyReal(value/oReal.doubleValue());
			};
	}

	@Override
	public PsyIntegral psyMod(final PsyRational oRational)
		throws PsyUndefinedResultException, PsyRangeCheckException
	{
		return switch(oRational)
			{
				case PsyInteger oInteger->
					{
						final var integer=oInteger.value; // TODO
						if(integer<0)
							throw new PsyRangeCheckException();
						if(integer==0)
							throw new PsyUndefinedResultException();
						yield of(Math.floorMod(value, integer));
					}
				case PsyBigInteger oBigInteger->
					{
						try
						{
							yield PsyIntegral.of(
									bigIntegerValue().mod(oBigInteger.bigIntegerValue()));
						}
						catch(final ArithmeticException ex)
						{
							throw new PsyRangeCheckException(ex);
						}
					}
				default->((PsyIntegral)psyMul(oRational.psyDenominator()))
						.psyMod(oRational.psyNumerator());
			};
	}

	@Override
	public PsyIntegral psyIdiv(final PsyRational oRational)
		throws PsyUndefinedResultException
	{
		return switch(oRational)
			{
				case PsyInteger oInteger->
					{
						if(oInteger==ZERO)
							throw new PsyUndefinedResultException();
						if(value==Long.MIN_VALUE && oInteger==MINUS_ONE)
							yield of(Long.MIN_VALUE).psyNeg();
						yield of(value/oInteger.value);
					}
				case PsyBigInteger oBigInteger->ZERO;
				default->((PsyIntegral)psyMul(oRational.psyDenominator()))
						.psyIdiv(oRational.psyNumerator());
			};
	}

	@Override
	public PsyInteger psyBitShift(final PsyInteger oShift)
	{
		return PsyInteger.of(oShift.value>=0? value<<oShift.value: value>>(-oShift.value));
	}

	// TODO more appropriate class
	public PsyBoolean psyInUnicodeBlock(final PsyTextual oTextual)
	{
		return PsyBoolean.of(Character.UnicodeBlock.of((int)value).equals(
				Character.UnicodeBlock.forName(oTextual.stringValue())));
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		return PsyBoolean.of(switch(o)
			{
				// TODO
				case PsyInteger oInteger->value==oInteger.value;
				//case PsyBigInteger oBigInteger->
				//	bigIntegerValue().equals(oBigInteger.bigIntegerValue());
				case PsyReal oReal->doubleValue()==oReal.doubleValue();
				case PsyComplex oComplex->
					doubleValue()==oComplex.psyRealPart().doubleValue()
							&& oComplex.psyImagPart().doubleValue()==0.D;	// TODO
				default->false;
			});
	}

	@Override
	public int hashCode()
	{
		return Long.hashCode(value);
	}

	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsyInteger oInteger && value==oInteger.value;
	}

	/**
	*	{@return a {@code integer} representing the specified value} This method will cache values
	*	in the range -128 to 127, inclusive.
	*
	*	@param longValue the specified value.
	*/
	public static PsyInteger of(final long longValue)
	{
		if(longValue>=-128 && longValue<128)
			return Cache.CACHE[(int)longValue+128];
		return new PsyInteger(longValue);
	}

	private static class Cache
	{
		static final PsyInteger[] CACHE=new PsyInteger[256];

		private Cache() {}

		static
		{
			for(int i=0; i<CACHE.length; i++)
				CACHE[i]=new PsyInteger(i-128);
		}
	}
}
