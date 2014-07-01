package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _if extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();
			PsiObject cond=opstack.pop();
			if(cond instanceof PsiBoolean)
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
