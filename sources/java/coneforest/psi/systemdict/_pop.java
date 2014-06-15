package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _pop extends PSIOperator
{
	public String getName()	{ return "pop"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
			opstack.pop();
	}
}
