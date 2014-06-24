package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _type extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			// TODO make name executable
			opstack.push(new PSIName(obj.getTypeName()+"type"));
		}
	}
}
