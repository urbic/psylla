package coneforest.psi;

public class PsiReal extends PsiNumeric
{
	public PsiReal(double value)
	{
		this.value=value;
	}
	
	public String getTypeName() { return "real"; }
	
	public Double getValue()
	{
		return value;
	}

	public PsiReal(Token token)
	{
		if(token.kind==ParserConstants.TOKEN_REAL)
		{
			this.value=new Double(token.image);
		}
	}

	public void setValue(double value)
	{
		this.value=value;
	}

	public String toString()
	{
		return new Double(value).toString();
	}

	private double value;
}
