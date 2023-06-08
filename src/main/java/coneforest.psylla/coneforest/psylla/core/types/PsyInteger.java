package coneforest.psylla.core.types;

import coneforest.psylla.Type;
import coneforest.psylla.core.errors.PsyError;
import coneforest.psylla.core.errors.PsyRangeCheck;
import coneforest.psylla.core.errors.PsyUndefinedResult;
import java.math.BigInteger;

/**
*	A representation of {@code integer}.
*/
@Type("integer")
public class PsyInteger
	implements
		PsyBitwise<PsyIntegral>,
		PsyIntegral
{
	public PsyInteger(final long value)
	{
		this.value=value;
	}

	@Override
	public PsyBoolean psyIsZero()
	{
		return PsyBoolean.of(value==0L);
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
		if(oIntegral instanceof PsyInteger oInteger)
			return PsyInteger.of(value | oInteger.value);
		return PsyIntegral.of(bigIntegerValue().or(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyAnd(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger oInteger)
			return PsyInteger.of(value & oInteger.value);
		return PsyIntegral.of(bigIntegerValue().and(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyXor(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger oInteger)
			return PsyInteger.of(value ^ oInteger.value);
		return PsyIntegral.of(bigIntegerValue().xor(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return value!=Long.MIN_VALUE?
			PsyInteger.of(-value): new PsyBigInteger(bigIntegerValue().negate());
	}

	// TODO
	@Override
	public PsyInteger psyCmp(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyInteger oInteger)
			return value<oInteger.value? MINUS_ONE:
				value>oInteger.value? ONE: ZERO;
		if(oRealNumeric instanceof PsyBigInteger oBigInteger)
			return PsyInteger.of(
					bigIntegerValue().compareTo(oBigInteger.bigIntegerValue()));
		return value<((PsyReal)oRealNumeric).doubleValue()? MINUS_ONE:
			value>((PsyReal)oRealNumeric).doubleValue()? ONE: ZERO;
	}

	@Override
	public PsyIntegral psyAbs()
	{
		return value>0L? this: psyNeg();
	}

	@Override
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyError
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheck();
		return PsyBoolean.of((value&(1L<<bit))!=0);
	}

	@Override
	public PsyInteger psyClearBit(final PsyInteger oBit)
		throws PsyError
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheck();
		return PsyInteger.of(value&~(1L<<bit));
	}

	@Override
	public PsyInteger psySetBit(final PsyInteger oBit)
		throws PsyError
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheck();
		return PsyInteger.of(value|(1L<<bit));
	}

	@Override
	public PsyInteger psyFlipBit(final PsyInteger oBit)
		throws PsyError
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheck();
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
		if(oRealNumeric instanceof PsyInteger oInteger)
		{
			final var numeric=oInteger.value;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedAdd(long, long)
			final var result=value+numeric;
			if((value^numeric)<0|(value^result)>=0)
				return PsyInteger.of(result);
			else
				return PsyIntegral.of(
					bigIntegerValue().add(oInteger.bigIntegerValue()));
		}
		if(oRealNumeric instanceof PsyBigInteger oBigInteger)
			return PsyIntegral.of(
				bigIntegerValue().add(oBigInteger.bigIntegerValue()));
		if(oRealNumeric instanceof PsyRational oRational)
			try
			{
				return psyMul(oRational.psyDenominator())
						.psyAdd(oRational.psyNumerator())
						.psyDiv(oRational.psyDenominator());
			}
			catch(final PsyUndefinedResult e)
			{
				// NOP
			}

		return new PsyReal(doubleValue()+oRealNumeric.doubleValue());
	}


	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyInteger oInteger)
		{
			final var numeric=oInteger.value;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedSubtract(long, long)
			final var result=value-numeric;
			if((value^numeric)>=0|(value^result)>=0)
				return PsyInteger.of(result);
			else
				return PsyIntegral.of(bigIntegerValue().subtract(oInteger.bigIntegerValue()));
		}
		if(oRealNumeric instanceof PsyBigInteger oBigInteger)
			return PsyIntegral.of(bigIntegerValue().subtract(oBigInteger.bigIntegerValue()));
		if(oRealNumeric instanceof PsyRational oRational)
			try
			{
				return psyMul(oRational.psyDenominator())
						.psySub(oRational.psyNumerator())
						.psyDiv(oRational.psyDenominator());
			}
			catch(final PsyUndefinedResult e)
			{
				// NOP
			}

		return new PsyReal(doubleValue()-oRealNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyInteger oInteger)
		{
			// Overflow condition from
			// com.google.common.math.LongMath.checkedMultiply(long, long)
			final var numeric=oInteger.value;
			final var leadingZeros
				=Long.numberOfLeadingZeros(value)
				+Long.numberOfLeadingZeros(~value)
				+Long.numberOfLeadingZeros(numeric)
				+Long.numberOfLeadingZeros(~numeric);
			if(leadingZeros>Long.SIZE+1)
				return PsyInteger.of(value*numeric);
			if(leadingZeros>=Long.SIZE && value>=0 | numeric!=Long.MIN_VALUE)
			{
				final var result=value*numeric;
				return (value==0 || result/value==numeric)?
					PsyInteger.of(result): new PsyBigInteger(bigIntegerValue().multiply(BigInteger.valueOf(numeric)));
			}
			else
				return PsyIntegral.of(bigIntegerValue().multiply(oInteger.bigIntegerValue()));
		}
		if(oRealNumeric instanceof PsyBigInteger oBigInteger)
			return PsyIntegral.of(bigIntegerValue().multiply(oBigInteger.bigIntegerValue()));
		if(oRealNumeric instanceof PsyRational oRational)
			try
			{
				return PsyRational.of((PsyIntegral)psyMul(oRational.psyNumerator()),
						oRational.psyDenominator());
			}
			catch(final PsyUndefinedResult e)
			{
				// NOP
			}

		return new PsyReal(doubleValue()*oRealNumeric.doubleValue());
	}

	@Override
	public int cmp(final PsyRealNumeric oRealNumeric)
	{
		if(oRealNumeric instanceof PsyInteger oInteger)
			return Long.compare(value, oInteger.value);
		if(oRealNumeric instanceof PsyBigInteger oBigInteger)
			return bigIntegerValue().compareTo(oBigInteger.bigIntegerValue());
		if(oRealNumeric instanceof PsyRational oRational)
			return psyMul(oRational.psyDenominator()).cmp(oRational.psyNumerator());
		return Double.compare(doubleValue(), oRealNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oRealNumeric)
		throws PsyUndefinedResult
	{
		if(oRealNumeric instanceof PsyIntegral oIntegral)
			return PsyRational.of(this, oIntegral);
		if(oRealNumeric instanceof PsyRational oRational)
			return PsyRational.of((PsyIntegral)psyMul(oRational.psyDenominator()),
					oRational.psyNumerator());

		return new PsyReal(doubleValue()/oRealNumeric.doubleValue());
	}

	/*
	@Override
	public PsyRealNumeric psyPow(final PsyInteger integer)
	{
		double resultValue=Math.pow(value, integer.doubleValue());
		if(resultValue>=Long.MIN_VALUE && resultValue<=Long.MAX_VALUE)
			return new PsyInteger(((Double)resultValue).longValue());
		return new PsyReal(resultValue);
	}
	*/

	/*
	@Override
	public PsyRealNumeric psyPow(final PsyNumeric cn)
	{
		double resultValue=Math.pow(value, cn.doubleValue());
		if(numeric instanceof PsyInteger
				&& resultValue>=Long.MIN_VALUE
				&& resultValue<=Long.MAX_VALUE)
			//return new PsyInteger(((Double)resultValue).longValue());
			return new PsyInteger(resultValue);
		else if(numeric instanceof PsyReal)
			return new PsyReal(result);
		else if(numeric instanceof PsyComplex)
			return psyPow((PsyComplex)numeric);
	}
	*/

	/*
	@Override
	public PsyNumeric psyPow(final PsyNumeric cn)
		throws PsyError
	{
		if(cn instanceof PsyInteger)
		{
			long cnValue=((PsyInteger)cn).value;
			if(cnValue<0)
				throw new PsyRangeCheck();
			if(value>=-2 & value<=2)
			{
				switch((int)value)
				{
					case 0:
						return new PsyInteger((cnValue==0)? 1: 0);
					case 1:
						return new PsyInteger.MINUS_ONE;
					case -1:
						return new PsyInteger(((cnValue&1)==0)? 1: -1);
					case 2:
						return (cnValue<Long.SIZE-1)?
							new PsyInteger(1L<<cnValue):
							new PsyReal(Math.pow(value, cnValue));
					case -2:
						return (cnValue<Long.SIZE)?
							new PsyInteger(((cnValue&1)==0)? (1L<<cnValue): (-1L<<cnValue)):
							new PsyReal(Math.pow(value, cnValue));
					default:
						throw new AssertionError();
				}
			}
			long accum=1;
			while(true)
			{
				if(cnValue==0)
					return new PsyInteger(accum);
				else if(cnValue==1)
					return psyMul()
			}
		}
		else if(cn instanceof PsyReal)
			return new PsyReal(Math.pow(value, ((PsyReal)cn).doubleValue()));
		else
			return super.psyPow((PsyComplex)cn);
	}
	*/

	@Override
	public PsyIntegral psyMod(final PsyIntegral oIntegral)
		throws PsyUndefinedResult, PsyRangeCheck
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResult();
		if(oIntegral instanceof PsyInteger oInteger)
		{
			final var integer=oInteger.value; // TODO
			if(integer<0)
				throw new PsyRangeCheck();
			if(integer==0)
				throw new PsyUndefinedResult();
			return PsyInteger.of(Math.floorMod(value, integer));
		}
		else
		{
			try
			{
				return PsyIntegral.of(
						bigIntegerValue().mod(((PsyBigInteger)oIntegral).bigIntegerValue()));
			}
			catch(final ArithmeticException ex)
			{
				throw new PsyRangeCheck();
			}
		}
	}

	@Override
	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyUndefinedResult
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResult();
		if(oIntegral instanceof PsyInteger oInteger)
		//if(((PsyInteger)oInteger).value==0) // TODO
		//throw new PsyUndefinedResult();
			return PsyInteger.of(value/oInteger.value); // TODO
		//if(oIntegral instanceof PsyBigInteger)
		return PsyIntegral.of(bigIntegerValue().divide(oIntegral.bigIntegerValue()));
	}

	@Override
	public PsyInteger psyBitShift(final PsyInteger oShift)
	{
		return PsyInteger.of(oShift.value>=0? value<<oShift.value: value>>(-oShift.value));
	}

	public PsyBoolean psyInUnicodeBlock(final PsyTextual oTextual)
	{
		return PsyBoolean.of(Character.UnicodeBlock.of((int)value).equals(
				Character.UnicodeBlock.forName(oTextual.stringValue())));
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		if(o instanceof PsyInteger oInteger)
			return PsyBoolean.of(value==oInteger.value);
		if(o instanceof PsyBigInteger oBigInteger)
			return PsyBoolean.of(bigIntegerValue().equals(oBigInteger.bigIntegerValue()));
		else if(o instanceof PsyReal oReal)
			return PsyBoolean.of(doubleValue()==oReal.doubleValue());
		else if(o instanceof PsyComplex oComplex)
			return PsyBoolean.of(doubleValue()==oComplex.psyRealPart().doubleValue()
						&& oComplex.psyImagPart().doubleValue()==0.D);
		return PsyBoolean.FALSE;
	}

	@Override
	public int hashCode()
	{
		return Long.hashCode(value);
	}

	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsyInteger oInteger
				&& value==oInteger.value;
	}

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

	public static PsyInteger of(final long integer)
	{
		if(integer>=-128 && integer<128)
			return Cache.cache[(int)integer+128];
		return new PsyInteger(integer);
	}

	private static class Cache
	{
		private Cache() {}

		static final PsyInteger cache[]=new PsyInteger[256];

		static
		{
			for(int i=0; i<cache.length; i++)
				cache[i]=new PsyInteger(i-128);
		}
	}

	//private static final PsyNamespace NAMESPACE=PsyNamespace.namespace(PsyInteger.class);

	//public static void register(final Interpreter interpreter)
	//{
		/*return java.lang.invoke.MethodHandles.lookup().lookupClass()
			.getAnnotation(Type.class).value();
		*/

		//final String prefix=PsyInteger.class.getAnnotation(Type.class).value();
		//interpreter.namespacePool().obtain(prefix);
		//System.out.println("Registered: "+prefix);

		//System.out.println(getClass().getAnnotation(Type.class).value());
	//}

}
