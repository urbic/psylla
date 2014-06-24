package coneforest.psi;

public class PSIMark extends PSIObject
{
	public String getTypeName() { return "mark"; }

	public void execute(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return "-mark-";
	}
}
