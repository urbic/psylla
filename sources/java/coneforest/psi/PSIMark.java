package coneforest.psi;

public class PSIMark extends PSIObject
{
	public byte getType()
	{
		return TYPE_MARK;
	}
	
	public void execute(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return "-mark-";
	}
}
