package coneforest.psi;

public class PSIBoolean extends PSIObject
{
	public PSIBoolean(boolean value)
	{
		this.value=value;
	}

	public String getTypeName() { return "boolean"; }
	
	public Boolean getValue()
	{
		return value;
	}

	public void execute(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return value? "true": "false";
	}

	static public PSIBoolean not(PSIBoolean p)
	{
		return new PSIBoolean(!p.getValue());
	}

	static public PSIBoolean and(PSIBoolean p, PSIBoolean q)
	{
		return new PSIBoolean(p.getValue() && q.getValue());
	}

	static public PSIBoolean or(PSIBoolean p, PSIBoolean q)
	{
		return new PSIBoolean(p.getValue() || q.getValue());
	}

	static public PSIBoolean xor(PSIBoolean p, PSIBoolean q)
	{
		return new PSIBoolean(p.getValue() ^ q.getValue());
	}

	private boolean value;
}
