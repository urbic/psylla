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

	private String value;
}
