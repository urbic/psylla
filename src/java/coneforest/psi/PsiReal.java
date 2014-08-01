package coneforest.psi;

public class PsiReal extends PsiNumeric
{
	public PsiReal(final double value)
	{
		this.value=value;
	}

	@Override
	public String getTypeName() { return "real"; }

	@Override
	public int intValue()
	{
		return (int)value;
	}

	@Override
	public long longValue()
	{
		return (long)value;
	}

	@Override
	public double doubleValue()
	{
		return value;
	}

	@Override
	public Double getValue()
	{
		return value;
	}

	@Override
	public PsiReal psiRe()
	{
		return new PsiReal(value);
	}

	@Override
	public PsiReal psiIm()
	{
		return new PsiReal(0.D);
	}

	@Override
	public PsiNumeric psiConjugate()
	{
		return new PsiReal(value);
	}

	@Override
	public PsiReal psiNeg()
	{
		return new PsiReal(-value);
	}

	@Override
	public PsiReal psiAbs()
	{
		return new PsiReal(Math.abs(value));
	}

	@Override
	public PsiReal psiSignum()
	{
		return new PsiReal(Math.signum(value));
	}

	@Override
	public PsiComplexNumeric psiAdd(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return new PsiReal(value+((PsiNumeric)cn).getValue().doubleValue());
		return super.psiAdd(cn);
	}

	//@Override
	public PsiReal psiSub(final PsiNumeric numeric)
	{
		return new PsiReal(value-numeric.getValue().doubleValue());
	}

	//@Override
	public PsiReal psiMul(final PsiNumeric numeric)
	{
		return new PsiReal(value*numeric.getValue().doubleValue());
	}

	@Override
	public PsiComplex psiMul(final PsiComplexNumeric cn)
	{
		return new PsiComplex(this).psiMul(cn);
	}

	@Override
	public PsiReal pow(final PsiNumeric numeric)
	{
		return new PsiReal(Math.pow(value, numeric.getValue().doubleValue()));
	}

	@Override
	public PsiReal psiFloor()
	{
		return new PsiReal(Math.floor(value));
	}

	@Override
	public PsiReal psiCeiling()
	{
		return new PsiReal(Math.ceil(value));
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}
	
	@Override
	public int hashCode()
	{
		return (int)value;
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof PsiReal
				&& psiEq((PsiReal)object).getValue();
	}

	private final double value;
}
