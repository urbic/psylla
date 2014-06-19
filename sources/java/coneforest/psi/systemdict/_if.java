package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _if extends PSIOperator
{
	public String getName()	{ return "if"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			PSIObject cond=opstack.pop();
			if(cond instanceof PSIBoolean)
			{
				if((boolean)cond.getValue())
					obj.invoke(interpreter);
				//interpreter.handleExecutionStack();
			}
			else
			{
				opstack.push(cond);
				opstack.push(obj);
				interpreter.error("typecheck");
				return;
			}
		}
	}
}
