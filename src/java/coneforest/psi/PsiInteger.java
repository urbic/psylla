package coneforest.psi;

/**
 *	A representation of Ψ <code class="type">integer</code> object.
 */
public class PsiInteger
	extends PsiNumeric
	implements PsiLogical<PsiInteger>
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
	public PsiInteger psiRe()
	{
		return new PsiInteger(value);
	}

	@Override
	public PsiInteger psiIm()
	{
		return PsiInteger.ZERO;
	}

	@Override
	public PsiInteger psiConjugate()
	{
		return new PsiInteger(value);
	}

	@Override
	public String getTypeName()	{ return "integer"; }

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@Override
	public PsiInteger psiNot()
	{
		return new PsiInteger(~value);
	}

	@Override
	public PsiInteger psiOr(final PsiInteger integer)
	{
		return new PsiInteger(value | integer.value);
	}

	@Override
	public PsiInteger psiAnd(final PsiInteger integer)
	{
		return new PsiInteger(value & integer.value);
	}

	@Override
	public PsiInteger psiXor(final PsiInteger obj)
	{
		return new PsiInteger(value ^ obj.value);
	}

	@Override
	public PsiNumeric psiNeg()
	{
		return value!=Long.MIN_VALUE?
			new PsiInteger(-value): new PsiReal(-value);
	}

	@Override
	public PsiInteger psiCmp(final PsiNumeric numeric)
	{
		if(numeric instanceof PsiInteger)
			return value<((PsiInteger)numeric).value? PsiInteger.MINUS_ONE:
				value>((PsiInteger)numeric).value? PsiInteger.ONE:
				PsiInteger.ZERO;
		else
			return value<((PsiReal)numeric).doubleValue()? PsiInteger.MINUS_ONE:
				value>((PsiReal)numeric).doubleValue()? PsiInteger.ONE:
				PsiInteger.ZERO;
	}

	@Override
	public PsiNumeric psiAbs()
	{
		if(value!=Long.MIN_VALUE)
			return new PsiInteger(value>=0? value: -value);
		else
			return new PsiReal(Math.abs(value));
	}

	@Override
	public PsiInteger psiSignum()
	{
		return new PsiInteger(value>0? 1: value<0? -1: 0);
	}

	public PsiNumeric psiAdd(final PsiNumeric numeric)
	{
		if(numeric instanceof PsiInteger)
		{
			long numericValue=((PsiInteger)numeric).value;
			long resultValue=value+numericValue;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedAdd(long, long)
			return ((value^numericValue)<0|(value^resultValue)>=0)?
					new PsiInteger(resultValue): new PsiReal(doubleValue()+numeric.doubleValue());
		}
		else
			return new PsiReal(doubleValue()+numeric.doubleValue());
	}

	@Override
	public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiAdd((PsiNumeric)cn);
		return super.psiAdd(cn);
	}

	public PsiNumeric psiSub(final PsiNumeric numeric)
	{
		if(numeric instanceof PsiInteger)
		{
			long numericValue=((PsiInteger)numeric).value;
			long resultValue=value-numericValue;

			// Overflow condition from
			// com.google.common.math.LongMath.checkedSubtract(long, long)
			return ((value^numericValue)>=0|(value^resultValue)>=0)?
					new PsiInteger(resultValue): new PsiReal(doubleValue()-numeric.doubleValue());
		}
		else
			return new PsiReal(doubleValue()+numeric.doubleValue());
	}

	@Override
	public PsiComplexNumeric psiSub(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiSub((PsiNumeric)cn);
		return super.psiSub(cn);
	}

	public PsiNumeric psiMul(final PsiNumeric numeric)
	{
		/*
		double result=value*numeric.doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
		*/
		if(numeric instanceof PsiInteger)
		{
			// Overflow condition from
			// com.google.common.math.LongMath.checkedMultiply(long, long)
			long numericValue=((PsiInteger)numeric).value;
			int leadingZeros
				=Long.numberOfLeadingZeros(value)
				+Long.numberOfLeadingZeros(~value)
				+Long.numberOfLeadingZeros(numericValue)
				+Long.numberOfLeadingZeros(~numericValue);
			if(leadingZeros>Long.SIZE+1)
				return new PsiInteger(value*numericValue);

			if(leadingZeros>=Long.SIZE && value>=0 | numericValue!=Long.MIN_VALUE)
			{
				long resultValue=value*numericValue;
				return (value==0 || resultValue/value==numericValue)?
					new PsiInteger(resultValue): new PsiReal(doubleValue()*numeric.doubleValue());
			}
			else
				return new PsiReal(doubleValue()*numeric.doubleValue());

		}
		return new PsiReal(doubleValue()*numeric.doubleValue());

	}

	@Override
	public PsiComplexNumeric psiMul(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiMul((PsiNumeric)cn);
		return super.psiMul(cn);
	}

	/*
	@Override
	public PsiNumeric psiPow(final PsiInteger integer)
	{
		double resultValue=Math.pow(value, integer.doubleValue());
		if(resultValue>=Long.MIN_VALUE && resultValue<=Long.MAX_VALUE)
			return new PsiInteger(((Double)resultValue).longValue());
		return new PsiReal(resultValue);
	}
	*/

	/*
	@Override
	public PsiNumeric psiPow(final PsiComplexNumeric cn)
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
	public PsiComplexNumeric psiPow(final PsiComplexNumeric cn)
		throws PsiException
	{
		if(cn instanceof PsiInteger)
		{
			long cnValue=((PsiInteger)cn).value;
			if(cnValue<0)
				throw new PsiException("rangecheck");
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
		return new PsiInteger(value);
	}

	@Override
	public PsiInteger psiRound()
	{
		return new PsiInteger(value);
	}

	@Override
	public PsiInteger psiCeiling()
	{
		return new PsiInteger(value);
	}

	public PsiInteger psiMod(final PsiInteger integer)
		throws PsiException
	{
		long integerValue=integer.value;
		if(integerValue<=0)
			throw new PsiException("rangecheck");
		long resultValue=value % integerValue;
		return new PsiInteger((resultValue>=0)? resultValue: resultValue+integerValue);
		/*
		if(integer.value>0)
			return new PsiInteger(value>=0? value%integer.value: integer.value-(-value)%integer.value);
		if(integer.value<0)
			return new PsiInteger(value>=0? value%(-integer.value)+(value!=0? integer.value:0): -((-value)%(-integer.value)));
		throw new PsiException("undefinedresult");
		*/
	}

	public PsiInteger psiIdiv(final PsiInteger integer)
		throws PsiException
	{
		if(integer.value==0)
			throw new PsiException("undefinedresult");
		return new PsiInteger(value/integer.value);
	}

	public PsiInteger psiBitShift(final PsiInteger shift)
	{
		return new PsiInteger(shift.value>=0? value<<shift.value: value>>(-shift.value));
	}

	@Override
	public int hashCode()
	{
		return (int)value;
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiInteger
				&& psiEq((PsiInteger)object).booleanValue();
	}

	public static final PsiInteger
		ZERO=new PsiInteger(0L),
		ONE=new PsiInteger(1L),
		TWO=new PsiInteger(1L),
		MINUS_ONE=new PsiInteger(-1L);

	private final long value;

	static
	{
		TypeRegistry.put("integer", PsiInteger.class);
	}
}
