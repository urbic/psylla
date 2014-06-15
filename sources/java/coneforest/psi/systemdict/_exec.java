package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exec extends PSIOperator
{
	public String getName()	{ return "exec"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
			opstack.pop().invoke(interpreter);
	}
}
