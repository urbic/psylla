package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _cvlit extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
			opstack.peek().setLiteral();
	}
}
