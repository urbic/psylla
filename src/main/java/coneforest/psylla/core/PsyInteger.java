package coneforest.psylla.core;
import coneforest.psylla.*;

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
		return PsyBoolean.valueOf(value==0L);
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
	public java.math.BigInteger bigIntegerValue()
	{
		return java.math.BigInteger.valueOf(value);
	}

	@Override
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public PsyInteger psyNot()
	{
		return PsyInteger.valueOf(~value);
	}

	@Override
	public PsyIntegral psyOr(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger)
			return PsyInteger.valueOf(value | ((PsyInteger)oIntegral).value);
		return PsyIntegral.valueOf(bigIntegerValue().or(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyAnd(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger)
			return PsyInteger.valueOf(value & ((PsyInteger)oIntegral).value);
		return PsyIntegral.valueOf(bigIntegerValue().and(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyIntegral psyXor(final PsyIntegral oIntegral)
	{
		if(oIntegral instanceof PsyInteger)
			return PsyInteger.valueOf(value ^ ((PsyInteger)oIntegral).value);
		return PsyIntegral.valueOf(bigIntegerValue().xor(((PsyBigInteger)oIntegral).bigIntegerValue()));
	}

	@Override
	public PsyRealNumeric psyNeg()
	{
		return value!=Long.MIN_VALUE?
			PsyInteger.valueOf(-value): new PsyBigInteger(bigIntegerValue().negate());
	}

	@Override
	public PsyInteger psyCmp(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
			return value<((PsyInteger)oNumeric).value? MINUS_ONE:
				value>((PsyInteger)oNumeric).value? ONE: ZERO;
		else if(oNumeric instanceof PsyBigInteger)
			return PsyInteger.valueOf(
					bigIntegerValue().compareTo(((PsyBigInteger)oNumeric).bigIntegerValue()));
		else
			return value<((PsyReal)oNumeric).doubleValue()? MINUS_ONE:
				value>((PsyReal)oNumeric).doubleValue()? ONE: ZERO;
	}

	@Override
	public PsyRealNumeric psyAbs()
	{
		return value>0L? this: psyNeg();
	}

	@Override
	public PsyBoolean psyTestBit(final PsyInteger oBit)
		throws PsyException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyBoolean.valueOf((value&(1L<<bit))!=0);
	}

	@Override
	public PsyInteger psyClearBit(final PsyInteger oBit)
		throws PsyException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.valueOf(value&~(1L<<bit));
	}

	@Override
	public PsyInteger psySetBit(final PsyInteger oBit)
		throws PsyException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.valueOf(value|(1L<<bit));
	}

	@Override
	public PsyInteger psyFlipBit(final PsyInteger oBit)
		throws PsyException
	{
		final var bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsyRangeCheckException();
		return PsyInteger.valueOf(value^(1L<<bit));
	}

	@Override
	public PsyInteger psySignum()
	{
		return PsyInteger.valueOf(Long.signum(value));
	}

	@Override
	public PsyRealNumeric psyAdd(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
		{
			final var numeric=((PsyInteger)oNumeric).value;
			final var result=value+numeric;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedAdd(long, long)
			if((value^numeric)<0|(value^result)>=0)
				return PsyInteger.valueOf(result);
			else
				return PsyIntegral.valueOf(
					bigIntegerValue().add(((PsyInteger)oNumeric).bigIntegerValue()));
		}
		else if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.valueOf(
				bigIntegerValue().add(((PsyBigInteger)oNumeric).bigIntegerValue()));

		return new PsyReal(doubleValue()+oNumeric.doubleValue());
	}

	@Override
	public PsyRealNumeric psySub(final PsyRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsyInteger)
		{
			final var numeric=((PsyInteger)oNumeric).value;
			final var result=value-numeric;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedSubtract(long, long)
			if((value^numeric)>=0|(value^result)>=0)
				return PsyInteger.valueOf(result);
			else
				return PsyIntegral.valueOf(bigIntegerValue().subtract(((PsyInteger)oNumeric).bigIntegerValue()));
		}
		else if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.valueOf(bigIntegerValue().subtract(((PsyBigInteger)oNumeric).bigIntegerValue()));

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
				return PsyInteger.valueOf(value*numeric);

			if(leadingZeros>=Long.SIZE && value>=0 | numeric!=Long.MIN_VALUE)
			{
				final var result=value*numeric;
				return (value==0 || result/value==numeric)?
					PsyInteger.valueOf(result): new PsyReal(doubleValue()*oNumeric.doubleValue());
			}
			else
				return PsyIntegral.valueOf(bigIntegerValue().multiply(((PsyInteger)oNumeric).bigIntegerValue()));
		}
		else if(oNumeric instanceof PsyBigInteger)
			return PsyIntegral.valueOf(bigIntegerValue().multiply(((PsyBigInteger)oNumeric).bigIntegerValue()));

		return new PsyReal(doubleValue()*oNumeric.doubleValue());
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
		throws PsyException
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
	public PsyInteger psyFloor()
	{
		return this;
	}

	@Override
	public PsyInteger psyRound()
	{
		return this;
	}

	@Override
	public PsyInteger psyCeiling()
	{
		return this;
	}

	@Override
	public PsyInteger psyMod(final PsyIntegral oInteger)
		throws PsyException
	{
		final var integer=((PsyInteger)oInteger).value; // TODO
		if(integer<=0)
			throw new PsyRangeCheckException();
		final var result=value % integer;
		return PsyInteger.valueOf((result>=0)? result: result+integer);
		/*
		if(integer.value>0)
			return new PsyInteger(value>=0? value%integer.value: integer.value-(-value)%integer.value);
		if(integer.value<0)
			return new PsyInteger(value>=0? value%(-integer.value)+(value!=0? integer.value:0): -((-value)%(-integer.value)));
		throw new PsyUndefinedResultException();
		*/
	}

	@Override
	public PsyInteger psyIdiv(final PsyIntegral oInteger)
		throws PsyException
	{
		if(((PsyInteger)oInteger).value==0) // TODO
			throw new PsyUndefinedResultException();
		return PsyInteger.valueOf(value/((PsyInteger)oInteger).value); // TODO
	}

	@Override
	public PsyInteger psyBitShift(final PsyInteger oShift)
	{
		return PsyInteger.valueOf(oShift.value>=0? value<<oShift.value: value>>(-oShift.value));
	}

	public PsyBoolean psyInUnicodeBlock(final PsyTextual oTextual)
	{
		return PsyBoolean.valueOf(Character.UnicodeBlock.of((int)value).equals(
				Character.UnicodeBlock.forName(oTextual.stringValue())));
	}

	@Override
	public PsyBoolean psyEq(final PsyObject o)
	{
		if(o instanceof PsyInteger)
			return PsyBoolean.valueOf(value==((PsyInteger)o).value);
		else if(o instanceof PsyReal)
			return PsyBoolean.valueOf(doubleValue()==((PsyReal)o).doubleValue());
		else if(o instanceof PsyBigInteger)
			return PsyBoolean.valueOf(new PsyBigInteger(value).equals(((PsyBigInteger)o)));
		else if(o instanceof PsyComplex)
			return PsyBoolean.valueOf(
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
	public static final PsyInteger ZERO=PsyInteger.valueOf(0L);

	/**
	*	An {@code integer} representing the number 1.
	*/
	public static final PsyInteger ONE=PsyInteger.valueOf(1L);

	/**
	*	An {@code integer} representing the number 2.
	*/
	public static final PsyInteger TWO=PsyInteger.valueOf(2L);

	/**
	*	An {@code integer} representing the number −1.
	*/
	public static final PsyInteger MINUS_ONE=PsyInteger.valueOf(-1L);

	/**
	*	An {@code integer} representing the maximum representable value.
	*/
	public static final PsyInteger MAX_VALUE=PsyInteger.valueOf(Long.MAX_VALUE);

	/**
	*	An {@code integer} representing the minimum representable value.
	*/
	public static final PsyInteger MIN_VALUE=PsyInteger.valueOf(Long.MIN_VALUE);

	private final long value;

	public static PsyInteger valueOf(final long integer)
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
