package coneforest.psi;

public class PsiNull extends PsiObject
{
	public String getTypeName() { return "null"; }

	public void invoke(Interpreter interpreter)
	{
		if(isLiteral())
			interpreter.getOperandStack().push(this);
	}

	public String toString()
	{
		return "null";
	}
}
