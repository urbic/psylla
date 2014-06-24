package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exch extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj1=opstack.pop();
			PSIObject obj2=opstack.pop();
			opstack.push(obj1);
			opstack.push(obj2);
		}
	}
}
