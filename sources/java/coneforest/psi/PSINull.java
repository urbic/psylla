package coneforest.psi;

public class PSINull extends PSIObject
{
	public String getTypeName() { return "null"; }

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
