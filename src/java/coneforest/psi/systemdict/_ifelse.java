package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _ifelse extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj2=opstack.pop();
			PsiObject obj1=opstack.pop();
			PsiObject cond=opstack.pop();
			if(cond instanceof PsiBoolean)
			{
				int execlevel=interpreter.pushLoopLevel();
				(((PsiBoolean)cond).getValue()? obj1: obj2).invoke(interpreter);
				interpreter.handleExecutionStack(execlevel);
			}
			else
			{
				opstack.push(cond);
				opstack.push(obj1);
				opstack.push(obj2);
				interpreter.error("typecheck");
			}
		}
	}
}
