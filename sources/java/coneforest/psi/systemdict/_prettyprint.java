package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _prettyprint extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		else
			System.out.println(opstack.pop());
	}
}
