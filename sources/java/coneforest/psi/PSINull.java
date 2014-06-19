package coneforest.psi;

public class PSINull extends PSIObject
{
	public byte getType()
	{
		return TYPE_NULL;
	}

	public void invoke(PSIInterpreter interpreter)
	{
		if(isLiteral())
			interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return "null";
	}
}
