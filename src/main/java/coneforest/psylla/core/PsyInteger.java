package coneforest.psylla.core;
import coneforest.psylla.*;
import java.math.BigInteger;

/**
*	A representation of {@code integer} object.
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
		if(oIntegral instanceof PsyInteger)
			return PsyInteger.of(value | ((PsyInteger)oIntegral).value);
		return PsyIntegral.of(bigIntegerValue().or(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyAnd(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger)
			return PsyInteger.of(value & ((PsyInteger)oIntegral).value);
		return PsyIntegral.of(bigIntegerValue().and(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyXor(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger)
			return PsyInteger.of(value ^ ((PsyInteger)oIntegral).value);
		return PsyIntegral.of(bigIntegerValue().xor(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyNeg()
	{
		return value!=Long.MIN_VALUE?
			PsyInteger.of(-value): new PsyBigInteger(bigIntegerValue().negate());
	}

	@Override
	public PsyInteger psyCmp(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
			return value<((PsyInteger)oNumeric).value? MINUS_ONE:
				value>((PsyInteger)oNumeric).value? ONE: ZERO;
		else if(oNumeric instanceof PsyBigInteger)
			return PsyInteger.of(
					bigIntegerValue().compareTo(((PsyBigInteger)oNumeric).bigIntegerValue()));
		else
			return value<((PsyReal)oNumeric).doubleValue()? MINUS_ONE:
				value>((PsyReal)oNumeric).doubleValue()? ONE: ZERO;
	}

	@Override
	public PsyIntegral psyAbs()
	{
		return value>0L? this: psyNeg();
	}

	@Override
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyErrorException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyBoolean.of((value&(1L<<bit))!=0);
	}

	@Override
	public PsyInteger psyClearBit(final PsyInteger oBit)
		throws PsyErrorException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.of(value&~(1L<<bit));
	}

	@Override
	public PsyInteger psySetBit(final PsyInteger oBit)
		throws PsyErrorException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.of(value|(1L<<bit));
	}

	@Override
	public PsyInteger psyFlipBit(final PsyInteger oBit)
		throws PsyErrorException
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
	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
		{
			final var numeric=((PsyInteger)oNumeric).value;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedAdd(long, long)
			final var result=value+numeric;
			if((value^numeric)<0|(value^result)>=0)
				return PsyInteger.of(result);
			else
				return PsyIntegral.of(
					bigIntegerValue().add(((PsyInteger)oNumeric).bigIntegerValue()));
		}
		if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.of(
				bigIntegerValue().add(((PsyBigInteger)oNumeric).bigIntegerValue()));
		if(oNumeric instanceof PsyRational)
			try
			{
				return psyMul(((PsyRational)oNumeric).psyDenominator())
						.psyAdd(((PsyRational)oNumeric).psyNumerator())
						.psyDiv(((PsyRational)oNumeric).psyDenominator());
			}
			catch(final PsyUndefinedResultException e)
			{
				// NOP
			}

		return new PsyReal(doubleValue()+oNumeric.doubleValue());
	}


	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
		{
			final var numeric=((PsyInteger)oNumeric).value;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedSubtract(long, long)
			final var result=value-numeric;
			if((value^numeric)>=0|(value^result)>=0)
				return PsyInteger.of(result);
			else
				return PsyIntegral.of(bigIntegerValue().subtract(((PsyInteger)oNumeric).bigIntegerValue()));
		}
		if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.of(bigIntegerValue().subtract(((PsyBigInteger)oNumeric).bigIntegerValue()));
		if(oNumeric instanceof PsyRational)
			try
			{
				return psyMul(((PsyRational)oNumeric).psyDenominator())
						.psySub(((PsyRational)oNumeric).psyNumerator())
						.psyDiv(((PsyRational)oNumeric).psyDenominator());
			}
			catch(final PsyUndefinedResultException e)
			{
				// NOP
			}

		return new PsyReal(doubleValue()-oNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyMul(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
		{
			// Overflow condition from
			// com.google.common.math.LongMath.checkedMultiply(long, long)
			final var numeric=((PsyInteger)oNumeric).value;
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
				return PsyIntegral.of(bigIntegerValue().multiply(((PsyInteger)oNumeric).bigIntegerValue()));
		}
		if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.of(bigIntegerValue().multiply(((PsyBigInteger)oNumeric).bigIntegerValue()));
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

		return new PsyReal(doubleValue()*oNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psyDiv(final PsyRealNumeric oNumeric)
		throws PsyUndefinedResultException
	{
		if(oNumeric instanceof PsyIntegral)
			return PsyRational.of(this, (PsyIntegral)oNumeric);
		if(oNumeric instanceof PsyRational)
			return PsyRational.of(
					(PsyIntegral)psyMul(((PsyRational)oNumeric).psyDenominator()),
					((PsyRational)oNumeric).psyNumerator()
				);

		return new PsyReal(doubleValue()/oNumeric.doubleValue());
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
		throws PsyErrorException
	{
		if(cn instanceof PsyInteger)
		{
			long cnValue=((PsyInteger)cn).value;
			if(cnValue<0)
				throw new PsyRangeCheckException();
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
		throws PsyUndefinedResultException, PsyRangeCheckException
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		if(oIntegral instanceof PsyInteger)
		{
			final var integer=((PsyInteger)oIntegral).value; // TODO
			if(integer<0)
				throw new PsyRangeCheckException();
			if(integer==0)
				throw new PsyUndefinedResultException();
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
				throw new PsyRangeCheckException();
			}
		}
	}

	@Override
	public PsyIntegral psyIdiv(final PsyIntegral oIntegral)
		throws PsyUndefinedResultException
	{
		if(oIntegral.psyIsZero().booleanValue())
			throw new PsyUndefinedResultException();
		if(oIntegral instanceof PsyInteger)
		//if(((PsyInteger)oInteger).value==0) // TODO
		//throw new PsyUndefinedResultException();
			return PsyInteger.of(value/((PsyInteger)oIntegral).value); // TODO
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
		if(o instanceof PsyInteger)
			return PsyBoolean.of(value==((PsyInteger)o).value);
		else if(o instanceof PsyReal)
			return PsyBoolean.of(doubleValue()==((PsyReal)o).doubleValue());
		else if(o instanceof PsyBigInteger)
			return PsyBoolean.of(new PsyBigInteger(value).equals(((PsyBigInteger)o)));
		else if(o instanceof PsyComplex)
			return PsyBoolean.of(
					doubleValue()==((PsyComplex)o).psyRealPart().doubleValue()
						&& ((PsyComplex)o).psyImagPart().doubleValue()==0.D);
		return PsyBoolean.FALSE;
	}

	@Override
	public int hashCode()
	{
		return (int)value;
	}

	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsyInteger
				&& value==((PsyInteger)object).value;
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
