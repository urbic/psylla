package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _if extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();
		PsiObject cond=opstack.pop();
		try
		{
			if(((PsiBoolean)cond).getValue())
			{
				//int execlevel=interpreter.getExecutionStack().size();
				obj.invoke(interpreter);
				//interpreter.handleExecutionStack(execlevel);
			}
		}
		catch(ClassCastException e)
		{
			opstack.push(cond);
			opstack.push(obj);
			interpreter.error("typecheck", this);
		}
	}
}
