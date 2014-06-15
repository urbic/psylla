package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _clear extends PSIOperator
{
	public String getName()	{ return "clear"; }

	public void execute(PSIInterpreter interpreter)
	{
		interpreter.getOperandStack().clear();
	}
}
