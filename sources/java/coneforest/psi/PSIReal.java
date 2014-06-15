package coneforest.psi;

public class PSIReal extends PSINumeric
{
	public PSIReal(double value)
	{
		this.value=value;
	}
	
	public byte getType()
	{
		return TYPE_REAL;
	}
	
	public Double getValue()
	{
		return value;
	}

	public PSIReal(Token token)
	{
		if(token.kind==PSIParserConstants.TOKEN_REAL)
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
