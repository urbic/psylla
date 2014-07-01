package coneforest.psi;

public class PsiMark extends PsiObject
{
	public String getTypeName() { return "mark"; }

	public void execute(Interpreter interpreter)
	{
		interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return "-mark-";
	}
}
