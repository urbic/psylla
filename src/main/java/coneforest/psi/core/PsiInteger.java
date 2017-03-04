package coneforest.psi.core;
import coneforest.psi.*;

/**
*	A representation of Ψ-{@code integer} object.
*/
@Type("integer")
public class PsiInteger
	implements
		PsiBitwise<PsiInteger>,
		PsiRealNumeric
{
	public PsiInteger(final long value)
	{
		this.value=value;
	}

	@Override
	public PsiBoolean psiIsZero()
	{
		return PsiBoolean.valueOf(value==0L);
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
	public String toSyntaxString()
	{
		return String.valueOf(value);
	}

	@Override
	public PsiInteger psiNot()
	{
		return PsiInteger.valueOf(~value);
	}

	@Override
	public PsiInteger psiOr(final PsiInteger oInteger)
	{
		return PsiInteger.valueOf(value | oInteger.value);
	}

	@Override
	public PsiInteger psiAnd(final PsiInteger oInteger)
	{
		return PsiInteger.valueOf(value & oInteger.value);
	}

	@Override
	public PsiInteger psiXor(final PsiInteger oInteger)
	{
		return PsiInteger.valueOf(value ^ oInteger.value);
	}

	@Override
	public PsiRealNumeric psiNeg()
	{
		return value!=Long.MIN_VALUE?
			PsiInteger.valueOf(-value): new PsiReal(-(double)value);
	}

	@Override
	public PsiInteger psiCmp(final PsiRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsiInteger)
			return value<((PsiInteger)oNumeric).value? MINUS_ONE:
				value>((PsiInteger)oNumeric).value? ONE: ZERO;
		else
			return value<((PsiReal)oNumeric).doubleValue()? MINUS_ONE:
				value>((PsiReal)oNumeric).doubleValue()? ONE: ZERO;
	}

	@Override
	public PsiRealNumeric psiAbs()
	{
		if(value>0L)
			return this;
		return psiNeg();
	}

	@Override
	public PsiBoolean psiTestBit(final PsiInteger oBit)
		throws PsiException
	{
		long bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsiRangeCheckException();
		return PsiBoolean.valueOf((value&(1L<<bit))!=0);
	}

	@Override
	public PsiInteger psiClearBit(final PsiInteger oBit)
		throws PsiException
	{
		long bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsiRangeCheckException();
		return PsiInteger.valueOf(value&~(1L<<bit));
	}

	@Override
	public PsiInteger psiSetBit(final PsiInteger oBit)
		throws PsiException
	{
		long bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsiRangeCheckException();
		return PsiInteger.valueOf(value|(1L<<bit));
	}

	@Override
	public PsiInteger psiFlipBit(final PsiInteger oBit)
		throws PsiException
	{
		long bit=oBit.value;
		if(bit<0 || bit>Long.SIZE-1)
			throw new PsiRangeCheckException();
		return PsiInteger.valueOf(value^(1L<<bit));
	}

	@Override
	public PsiInteger psiSignum()
	{
		return PsiInteger.valueOf(Long.signum(value));
	}

	@Override
	public PsiRealNumeric psiAdd(final PsiRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsiInteger)
		{
			final long numeric=((PsiInteger)oNumeric).value;
			final long result=value+numeric;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedAdd(long, long)
			return ((value^numeric)<0|(value^result)>=0)?
					PsiInteger.valueOf(result): new PsiReal(doubleValue()+oNumeric.doubleValue());
		}
		else
			return new PsiReal(doubleValue()+oNumeric.doubleValue());
	}

	@Override
	public PsiRealNumeric psiSub(final PsiRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsiInteger)
		{
			final long numeric=((PsiInteger)oNumeric).value;
			final long result=value-numeric;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedSubtract(long, long)
			return ((value^numeric)>=0|(value^result)>=0)?
					PsiInteger.valueOf(result): new PsiReal(doubleValue()-oNumeric.doubleValue());
		}
		else
			return new PsiReal(doubleValue()-oNumeric.doubleValue());
	}

	@Override
	public PsiRealNumeric psiMul(final PsiRealNumeric oNumeric)
	{
		if(oNumeric instanceof PsiInteger)
		{
			// Overflow condition from
			// com.google.common.math.LongMath.checkedMultiply(long, long)
			final long numeric=((PsiInteger)oNumeric).value;
			final int leadingZeros
				=Long.numberOfLeadingZeros(value)
				+Long.numberOfLeadingZeros(~value)
				+Long.numberOfLeadingZeros(numeric)
				+Long.numberOfLeadingZeros(~numeric);
			if(leadingZeros>Long.SIZE+1)
				return PsiInteger.valueOf(value*numeric);

			if(leadingZeros>=Long.SIZE && value>=0 | numeric!=Long.MIN_VALUE)
			{
				final long result=value*numeric;
				return (value==0 || result/value==numeric)?
					PsiInteger.valueOf(result): new PsiReal(doubleValue()*oNumeric.doubleValue());
			}
			else
				return new PsiReal(doubleValue()*oNumeric.doubleValue());

		}
		else if(oNumeric instanceof PsiBigInteger)
		{
			return new PsiBigInteger(this).psiMul((PsiBigInteger)oNumeric);
		}
		return new PsiReal(doubleValue()*oNumeric.doubleValue());

	}

	/*
	@Override
	public PsiRealNumeric psiPow(final PsiInteger integer)
	{
		double resultValue=Math.pow(value, integer.doubleValue());
		if(resultValue>=Long.MIN_VALUE && resultValue<=Long.MAX_VALUE)
			return new PsiInteger(((Double)resultValue).longValue());
		return new PsiReal(resultValue);
	}
	*/

	/*
	@Override
	public PsiRealNumeric psiPow(final PsiNumeric cn)
	{
		double resultValue=Math.pow(value, cn.doubleValue());
		if(numeric instanceof PsiInteger
				&& resultValue>=Long.MIN_VALUE
				&& resultValue<=Long.MAX_VALUE)
			//return new PsiInteger(((Double)resultValue).longValue());
			return new PsiInteger(resultValue);
		else if(numeric instanceof PsiReal)
			return new PsiReal(result);
		else if(numeric instanceof PsiComplex)
			return psiPow((PsiComplex)numeric);
	}
	*/

	/*
	@Override
	public PsiNumeric psiPow(final PsiNumeric cn)
		throws PsiException
	{
		if(cn instanceof PsiInteger)
		{
			long cnValue=((PsiInteger)cn).value;
			if(cnValue<0)
				throw new PsiRangeCheckException();
			if(value>=-2 & value<=2)
			{
				switch((int)value)
				{
					case 0:
						return new PsiInteger((cnValue==0)? 1: 0);
					case 1:
						return new PsiInteger.MINUS_ONE;
					case -1:
						return new PsiInteger(((cnValue&1)==0)? 1: -1);
					case 2:
						return (cnValue<Long.SIZE-1)?
							new PsiInteger(1L<<cnValue):
							new PsiReal(Math.pow(value, cnValue));
					case -2:
						return (cnValue<Long.SIZE)?
							new PsiInteger(((cnValue&1)==0)? (1L<<cnValue): (-1L<<cnValue)):
							new PsiReal(Math.pow(value, cnValue));
					default:
						throw new AssertionError();
				}
			}
			long accum=1;
			while(true)
			{
				if(cnValue==0)
					return new PsiInteger(accum);
				else if(cnValue==1)
					return psiMul()
			}
		}
		else if(cn instanceof PsiReal)
			return new PsiReal(Math.pow(value, ((PsiReal)cn).doubleValue()));
		else
			return super.psiPow((PsiComplex)cn);
	}
	*/

	@Override
	public PsiInteger psiFloor()
	{
		return this;
	}

	@Override
	public PsiInteger psiRound()
	{
		return this;
	}

	@Override
	public PsiInteger psiCeiling()
	{
		return this;
	}

	public PsiInteger psiMod(final PsiInteger oInteger)
		throws PsiException
	{
		long integer=oInteger.value;
		if(integer<=0)
			throw new PsiRangeCheckException();
		long result=value % integer;
		return PsiInteger.valueOf((result>=0)? result: result+integer);
		/*
		if(integer.value>0)
			return new PsiInteger(value>=0? value%integer.value: integer.value-(-value)%integer.value);
		if(integer.value<0)
			return new PsiInteger(value>=0? value%(-integer.value)+(value!=0? integer.value:0): -((-value)%(-integer.value)));
		throw new PsiUndefinedResultException();
		*/
	}

	public PsiInteger psiIdiv(final PsiInteger oInteger)
		throws PsiException
	{
		if(oInteger.value==0)
			throw new PsiUndefinedResultException();
		return PsiInteger.valueOf(value/oInteger.value);
	}

	@Override
	public PsiInteger psiBitShift(final PsiInteger oShift)
	{
		return PsiInteger.valueOf(oShift.value>=0? value<<oShift.value: value>>(-oShift.value));
	}

	public PsiBoolean psiInUnicodeBlock(final PsiStringy oStringy)
	{
		return PsiBoolean.valueOf(Character.UnicodeBlock.of((int)value).equals(
				Character.UnicodeBlock.forName(oStringy.stringValue())));
	}

	@Override
	public PsiBoolean psiEq(final PsiObject o)
	{
		if(o instanceof PsiInteger)
			return PsiBoolean.valueOf(value==((PsiInteger)o).value);
		else if(o instanceof PsiReal)
			return PsiBoolean.valueOf(doubleValue()==((PsiReal)o).doubleValue());
		else if(o instanceof PsiBigInteger)
			return PsiBoolean.valueOf(new PsiBigInteger(value).equals(((PsiBigInteger)o)));
		else if(o instanceof PsiComplex)
			return PsiBoolean.valueOf(
					doubleValue()==((PsiComplex)o).psiRealPart().doubleValue()
						&& ((PsiComplex)o).psiImagPart().doubleValue()==0.D);
		return PsiBoolean.FALSE;
	}

	@Override
	public int hashCode()
	{
		return (int)value;
	}

	@Override
	public boolean equals(final Object object)
	{
		return object instanceof PsiInteger
				&& value==((PsiInteger)object).value;
	}

	/**
	*	A Ψ-{@code integer} representing the number 0.
	*/
	public static final PsiInteger ZERO=PsiInteger.valueOf(0L);

	/**
	*	A Ψ-{@code integer} representing the number 1.
	*/
	public static final PsiInteger ONE=PsiInteger.valueOf(1L);

	/**
	*	A Ψ-{@code integer} representing the number 2.
	*/
	public static final PsiInteger TWO=PsiInteger.valueOf(2L);

	/**
	*	A Ψ-{@code integer} representing the number −1.
	*/
	public static final PsiInteger MINUS_ONE=PsiInteger.valueOf(-1L);

	/**
	*	A Ψ-{@code integer} representing the maximum representable value.
	*/
	public static final PsiInteger MAX_VALUE=PsiInteger.valueOf(Long.MAX_VALUE);

	/**
	*	A Ψ-{@code integer} representing the minimum representable value.
	*/
	public static final PsiInteger MIN_VALUE=PsiInteger.valueOf(Long.MIN_VALUE);

	private final long value;

	public static PsiInteger valueOf(final long integer)
	{
		if(integer>=-128 && integer<128)
			return Cache.cache[(int)integer+128];
		return new PsiInteger(integer);
	}

	private static class Cache
	{
		private Cache() {}

		static final PsiInteger cache[]=new PsiInteger[256];

		static
		{
			for(int i=0; i<cache.length; i++)
				cache[i]=new PsiInteger(i-128);
		}
	}

	private static final PsiNamespace NAMESPACE=PsiNamespace.forName("integer");

	public static void register(final Interpreter interpreter)
	{
		/*return java.lang.invoke.MethodHandles.lookup().lookupClass()
			.getAnnotation(Type.class).value();
		*/
		
		final String prefix=PsiInteger.class.getAnnotation(Type.class).value();
		//interpreter.namespacePool().obtain(prefix);
		System.out.println("Registered: "+prefix);
		
		//System.out.println(getClass().getAnnotation(Type.class).value());
	}
}
