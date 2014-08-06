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
	public Long getValue()
	{
		return value;
	}

	@Override
	public PsiInteger psiRe()
	{
		return new PsiInteger(getValue());
	}

	@Override
	public PsiInteger psiIm()
	{
		return new PsiInteger(0);
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
		return new PsiInteger(~getValue());
	}

	@Override
	public PsiInteger psiOr(final PsiInteger obj)
	{
		return new PsiInteger(getValue() | obj.getValue());
	}

	@Override
	public PsiInteger psiAnd(final PsiInteger obj)
	{
		return new PsiInteger(getValue() & obj.getValue());
	}

	@Override
	public PsiInteger psiXor(final PsiInteger obj)
	{
		return new PsiInteger(getValue() ^ obj.getValue());
	}

	@Override
	public PsiNumeric psiNeg()
	{
		if(getValue()!=Long.MIN_VALUE)
			return new PsiInteger(-getValue());
		else
			return new PsiReal(-getValue());
	}

	@Override
	public PsiNumeric psiAbs()
	{
		if(value!=Long.MIN_VALUE)
			return new PsiInteger(value>=0L? value: -value);
		else
			return new PsiReal(Math.abs(value));
	}

	@Override
	public PsiInteger psiSignum()
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

	public PsiNumeric psiAdd(final PsiNumeric numeric)
	{
		double result=value+numeric.getValue().doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
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
		double result=value-numeric.getValue().doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
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
		double result=value*numeric.getValue().doubleValue();
		if(numeric instanceof PsiInteger
				&& result>=Long.MIN_VALUE
				&& result<=Long.MAX_VALUE)
			return new PsiInteger(((Double)result).longValue());
		return new PsiReal(result);
	}

	@Override
	public PsiComplexNumeric psiMul(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return psiMul((PsiNumeric)cn);
		return super.psiMul(cn);
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
	public PsiInteger psiFloor()
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
		if(integer.value>0)
			return new PsiInteger(value>=0? value%integer.value: integer.value-(-value)%integer.value);
		if(integer.value<0)
			return new PsiInteger(value>=0? value%(-integer.value)+(value!=0? integer.value:0): -((-value)%(-integer.value)));
		throw new PsiException("undefinedresult");
		/*
		long integerValue=integer.getValue();
		if(integerValue>0)
			return new PsiInteger(value>=0? value%integerValue: integerValue-(-value)%integerValue);
		if(integerValue<0)
			return new PsiInteger(value>=0? value%(-integerValue)+(value!=0? integerValue:0): -((-value)%(-integerValue)));
		throw new PsiException("undefinedresult");
		*/
	}

	public PsiInteger psiIdiv(final PsiInteger integer)
		throws PsiException
	{
		if(integer.getValue()==0.D)
			throw new PsiException("undefinedresult");
		return new PsiInteger(psiDiv(integer).getValue().longValue());
	}

	public PsiInteger psiBitShift(final PsiInteger shift)
	{
		long shiftValue=shift.getValue();
		return new PsiInteger(shiftValue>=0? (getValue()<<shiftValue): (getValue()>>(-shiftValue)));
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
				&& psiEq((PsiInteger)object).getValue();
	}

	private final long value;
}
