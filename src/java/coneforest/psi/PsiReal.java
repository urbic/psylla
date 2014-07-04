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

	public String toString()
	{
		return String.valueOf(value);
	}

	private final double value;
}
