package coneforest.psi;

public class PsiBigInteger
	implements
		PsiBitwise,
		PsiLogical<PsiBigInteger>,
		PsiNumeric
{
	public PsiBigInteger(java.math.BigInteger value)
	{
		this.value=value;
	}

	public PsiBigInteger(PsiInteger oInteger)
	{
		this.value=java.math.BigInteger.valueOf(oInteger.longValue());
	}

	@Override
	public String getTypeName()
	{
		return "biginteger";
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
	public PsiBigInteger psiBitShift(final PsiInteger oShift)
	{
		return new PsiBigInteger(value.shiftLeft(oShift.intValue()));
	}

	@Override
	public PsiBoolean psiTestBit(final PsiInteger oBit)
	{
		return PsiBoolean.valueOf(value.testBit(oBit.intValue()));
	}

	@Override
	public PsiBigInteger psiSetBit(final PsiInteger oBit)
	{
		return new PsiBigInteger(value.setBit(oBit.intValue()));
	}

	@Override
	public PsiBigInteger psiFlipBit(final PsiInteger oBit)
	{
		return new PsiBigInteger(value.flipBit(oBit.intValue()));
	}

	@Override
	public PsiBigInteger psiClearBit(final PsiInteger oBit)
	{
		return new PsiBigInteger(value.clearBit(oBit.intValue()));
	}

	@Override
	public PsiBigInteger psiOr(final PsiBigInteger oBigInteger)
	{
		return new PsiBigInteger(value.or(oBigInteger.value));
	}

	@Override
	public PsiBigInteger psiAnd(final PsiBigInteger oBigInteger)
	{
		return new PsiBigInteger(value.and(oBigInteger.value));
	}

	@Override
	public PsiBigInteger psiXor(final PsiBigInteger oBigInteger)
	{
		return new PsiBigInteger(value.xor(oBigInteger.value));
	}

	@Override
	public PsiBigInteger psiNot()
	{
		return new PsiBigInteger(value.not());
	}

	@Override
	public PsiBigInteger psiCeiling()
	{
		return this;
	}

	@Override
	public PsiBigInteger psiFloor()
	{
		return this;
	}

	@Override
	public PsiBigInteger psiRound()
	{
		return this;
	}

	@Override
	public PsiBigInteger psiAbs()
	{
		return new PsiBigInteger(value.abs());
	}

	@Override
	public PsiNumeric psiMul(final PsiNumeric oNumeric)
	{
		if(oNumeric instanceof PsiBigInteger)
			return new PsiBigInteger(value.multiply(((PsiBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsiInteger)
			return new PsiBigInteger(value.multiply(new PsiBigInteger((PsiInteger)oNumeric).value));
		return new PsiReal(doubleValue()*((PsiReal)oNumeric).doubleValue());
	}

	@Override
	public PsiNumeric psiSub(final PsiNumeric oNumeric)
	{
		if(oNumeric instanceof PsiBigInteger)
			return new PsiBigInteger(value.subtract(((PsiBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsiInteger)
			return new PsiBigInteger(value.subtract(new PsiBigInteger((PsiInteger)oNumeric).value));
		return new PsiReal(doubleValue()-((PsiReal)oNumeric).doubleValue());
	}

	@Override
	public PsiNumeric psiAdd(final PsiNumeric oNumeric)
	{
		if(oNumeric instanceof PsiBigInteger)
			return new PsiBigInteger(value.add(((PsiBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsiInteger)
			return new PsiBigInteger(value.add(new PsiBigInteger((PsiInteger)oNumeric).value));
		return new PsiReal(doubleValue()+((PsiReal)oNumeric).doubleValue());
	}

	@Override
	public PsiBigInteger psiNeg()
	{
		return new PsiBigInteger(value.negate());
	}

	@Override
	public PsiBigInteger psiRealPart()
	{
		return this;
	}

	@Override
	public PsiBigInteger psiImagPart()
	{
		return new PsiBigInteger(java.math.BigInteger.ZERO);
	}

	@Override
	public PsiBoolean psiIsZero()
	{
		return PsiBoolean.valueOf(value.equals(java.math.BigInteger.ZERO));
	}

	@Override
	public PsiBigInteger psiSignum()
	{
		return new PsiBigInteger(new PsiInteger(value.compareTo(java.math.BigInteger.ZERO)));
	}

	@Override
	public PsiInteger psiCmp(PsiNumeric oNumeric)
	{
		if(oNumeric instanceof PsiBigInteger)
			return PsiInteger.valueOf(value.compareTo(((PsiBigInteger)oNumeric).value));
		else if(oNumeric instanceof PsiInteger)
			return PsiInteger.valueOf(value.compareTo(java.math.BigInteger.valueOf(((PsiInteger)oNumeric).longValue())));
		return PsiInteger.valueOf(Double.valueOf(doubleValue()).compareTo(((PsiReal)oNumeric).doubleValue()));
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiBigInteger
				&& value.equals((PsiBigInteger)object);
	}

	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	private java.math.BigInteger value;
}
