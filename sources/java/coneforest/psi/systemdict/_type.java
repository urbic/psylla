package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _type extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			PSIName result=new PSIName(obj.getTypeName()+"type");
			result.setExecutable();
			opstack.push(result);
		}
	}
}
