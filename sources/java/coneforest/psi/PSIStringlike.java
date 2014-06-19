package coneforest.psi;

abstract public class PSIStringlike extends PSIObject
{
	abstract public byte getType();
	
	public PSIStringlike()
	{
	}
	
	public PSIStringlike(String value)
	{
		this.value=value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value=value;
	}
	
	public String toString()
	{
		if(this instanceof PSIString)
			return ((PSIString)this).toString();
		else
			return ((PSIName)this).toString();
	}
	
	public static PSIBoolean eq(final PSIStringlike x, final PSIStringlike y)
	{
		return new PSIBoolean(x.getValue().compareTo(y.getValue())==0);
	}

	public static PSIBoolean ne(final PSIStringlike x, final PSIStringlike y)
	{
		return new PSIBoolean(x.getValue().compareTo(y.getValue())!=0);
	}

	public static PSIBoolean lt(final PSIStringlike x, final PSIStringlike y)
	{
		return new PSIBoolean(x.getValue().compareTo(y.getValue())<0);
	}

	public static PSIBoolean le(final PSIStringlike x, final PSIStringlike y)
	{
		return new PSIBoolean(x.getValue().compareTo(y.getValue())<=0);
	}

	public static PSIBoolean gt(final PSIStringlike x, final PSIStringlike y)
	{
		return new PSIBoolean(x.getValue().compareTo(y.getValue())>0);
	}

	public static PSIBoolean ge(final PSIStringlike x, final PSIStringlike y)
	{
		return new PSIBoolean(x.getValue().compareTo(y.getValue())>=0);
	}

	private String value;
}
