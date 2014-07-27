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
	public Double getValue()
	{
		return value;
	}
	
	@Override
	public PsiReal re()
	{
		return new PsiReal(value);
	}

	@Override
	public PsiReal im()
	{
		return new PsiReal(0.D);
	}

	@Override
	public PsiNumeric conjugate()
	{
		return new PsiReal(value);
	}

	@Override
	public PsiReal neg()
	{
		return new PsiReal(-value);
	}

	@Override
	public PsiReal abs()
	{
		return new PsiReal(Math.abs(value));
	}

	@Override
	public PsiReal signum()
	{
		return new PsiReal(Math.signum(value));
	}
	
	@Override
	public PsiComplexNumeric add(final PsiComplexNumeric cn)
	{
		if(cn instanceof PsiNumeric)
			return new PsiReal(value+((PsiNumeric)cn).getValue().doubleValue());
		return super.add(cn);
	}
	
	//@Override
	public PsiReal sub(final PsiNumeric numeric)
	{
		return new PsiReal(value-numeric.getValue().doubleValue());
	}
	
	//@Override
	public PsiReal mul(final PsiNumeric numeric)
	{
		return new PsiReal(value*numeric.getValue().doubleValue());
	}

	@Override
	public PsiComplex mul(final PsiComplexNumeric complex)
	{
		return new PsiComplex(this).mul(complex);
	}
	
	@Override
	public PsiReal pow(final PsiNumeric numeric)
	{
		return new PsiReal(Math.pow(value, numeric.getValue().doubleValue()));
	}
	
	@Override
	public PsiReal floor()
	{
		return new PsiReal(Math.floor(value));
	}

	@Override
	public PsiReal ceiling()
	{
		return new PsiReal(Math.ceil(value));
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	private final double value;
}
