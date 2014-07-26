package coneforest.psi;

public class PsiReal extends PsiNumeric
{
	public PsiReal(final double value)
	{
		this.value=value;
	}
	
	public String getTypeName() { return "real"; }
	
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

	public PsiReal neg()
	{
		return new PsiReal(-value);
	}

	public PsiReal abs()
	{
		return new PsiReal(Math.abs(value));
	}
	
	public PsiReal add(final PsiNumeric numeric)
	{
		return new PsiReal(value+numeric.getValue().doubleValue());
	}
	
	public PsiReal sub(final PsiNumeric numeric)
	{
		return new PsiReal(value-numeric.getValue().doubleValue());
	}
	
	public PsiReal mul(final PsiNumeric numeric)
	{
		return new PsiReal(value*numeric.getValue().doubleValue());
	}	
	
	public PsiReal floor()
	{
		return new PsiReal(Math.floor(value));
	}

	public PsiReal ceiling()
	{
		return new PsiReal(Math.ceil(value));
	}

	public String toString()
	{
		return String.valueOf(value);
	}

	private final double value;
}
