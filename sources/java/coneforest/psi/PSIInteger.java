package coneforest.psi;

public class PSIInteger extends PSINumeric
{
	public PSIInteger(final long value)
	{
		this.value=value;
	}

	public byte getType()
	{
		return TYPE_INTEGER;
	}

	public Long getValue()
	{
		return value;
	}

	public void setValue(final long value)
	{
		this.value=value;
	}

	public PSIInteger(final Token token)
	{
		if(token.kind==PSIParserConstants.TOKEN_INTEGER)
			this.value=new Long(token.image);
		// TODO
	}

	public String toString()
	{
		return new Long(value).toString();
	}

	public static PSIInteger not(final PSIInteger x)
	{
		return new PSIInteger(~x.getValue());
	}

	public static PSIInteger and(final PSIInteger x, final PSIInteger y)
	{
		return new PSIInteger(x.getValue()&y.getValue());
	}

	public static PSIInteger or(final PSIInteger x, final PSIInteger y)
	{
		return new PSIInteger(x.getValue()|y.getValue());
	}

	public static PSIInteger xor(final PSIInteger x, final PSIInteger y)
	{
		return new PSIInteger(x.getValue()^y.getValue());
	}

	public static PSIInteger mod(final PSIInteger x, final PSIInteger y)
	{
		long xValue=x.getValue();
		long yValue=y.getValue();
		if(yValue>0)
			return new PSIInteger(xValue>=0? xValue%yValue: yValue-(-xValue)%yValue);
		if(yValue<0)
			return new PSIInteger(xValue>=0? xValue%(-yValue)+(xValue!=0? yValue:0): -((-xValue)%(-yValue)));
		return null;
	}

	private long value;
}
