package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _xcheck extends PSIOperator
{
	public String getName()	{ return "xcheck"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject obj=opstack.pop();
		opstack.push(new PSIBoolean(obj.isExecutable()));
	}
}
