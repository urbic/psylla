package coneforest.psi;

public class PsiInteger
	extends PsiNumeric
	implements PsiLogical<PsiInteger>
{
	public PsiInteger(final long value)
	{
		this.value=value;
	}

	@Override
	public Long getValue()
	{
		return value;
	}
	
	@Override
	public PsiInteger re()
	{
		return new PsiInteger(getValue());
	}

	@Override
	public PsiInteger im()
	{
		return new PsiInteger(0);
	}
	
	@Override
	public PsiInteger conjugate()
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
	public PsiInteger not()
	{
		return new PsiInteger(~getValue());
	}

	@Override
	public PsiInteger or(final PsiInteger obj)
	{
		return new PsiInteger(getValue() | obj.getValue());
	}

	@Override
	public PsiInteger and(final PsiInteger obj)
	{
		return new PsiInteger(getValue() & obj.getValue());
	}

	@Override
	public PsiInteger xor(final PsiInteger obj)
	{
		return new PsiInteger(getValue() ^ obj.getValue());
	}

	@Override
	public PsiNumeric neg()
	{
		if(getValue()!=Long.MIN_VALUE)
			return new PsiInteger(-getValue());
		else
			return new PsiReal(-getValue());
	}
	
	@Override
	public PsiNumeric abs()
	{
		if(value!=Long.MIN_VALUE)
			return new PsiInteger(value>=0L? value: -value);
		else
			return new PsiReal(Math.abs(value));
	}

	@Override
	public PsiInteger signum()
	{
		return new PsiInteger(value>0? 1: value<0? -1: 0);
	}

	/*
	@Override
	public PsiNumeric add(final PsiInteger integer)
	{
		double result=value+integer.getValue().doubleValue();
		if(result>=Long.MIN_VALUE && result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
	}

	@Override
	public PsiReal add(final PsiReal real)
	{
		return new PsiReal(value+real.getValue().doubleValue());
	}

	@Override
	public PsiComplex add(final PsiComplex complex)
	{
		return new PsiComplex(this).add(complex);
	}

	@Override
	public PsiComplexNumeric add(final PsiComplexNumeric cn)
		//throws PsiException
	{
		return null;
		//throw new PsiException("XXX");
	}
	*/
	
	public PsiNumeric add(final PsiNumeric numeric)
	{
		double result=value+numeric.getValue().doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
	}

	@Override
	public PsiComplexNumeric add(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return add((PsiNumeric)cn);
		return super.add(cn);
	}

	public PsiNumeric sub(final PsiNumeric numeric)
	{
		double result=value-numeric.getValue().doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
	}

	@Override
	public PsiComplexNumeric sub(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return sub((PsiNumeric)cn);
		return super.sub(cn);
	}

	public PsiNumeric mul(final PsiNumeric numeric)
	{
		double result=value*numeric.getValue().doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
	}

	@Override
	public PsiComplexNumeric mul(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return mul((PsiNumeric)cn);
		return super.mul(cn);
	}

	@Override
	public PsiNumeric pow(final PsiNumeric numeric)
	{
		double result=Math.pow(value, numeric.getValue().doubleValue());
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		else
			return new PsiReal(result);
	}

	@Override
	public PsiInteger floor()
	{
		return new PsiInteger(value);
	}

	@Override
	public PsiInteger ceiling()
	{
		return new PsiInteger(value);
	}

	//@Override
	public PsiInteger mod(final PsiInteger integer)
	{
		long integerValue=integer.getValue();
		if(integerValue>0)
			return new PsiInteger(value>=0? value%integerValue: integerValue-(-value)%integerValue);
		if(integerValue<0)
			return new PsiInteger(value>=0? value%(-integerValue)+(value!=0? integerValue:0): -((-value)%(-integerValue)));
		return null;
	}

	//@Override
	public static PsiInteger bitshift(final PsiInteger x, final PsiInteger shift)
	{
		long xValue=x.getValue();
		long shiftValue=shift.getValue();
		return new PsiInteger(shiftValue>=0? (xValue<<shiftValue): (xValue>>(-shiftValue)));
	}

	private final long value;
}
