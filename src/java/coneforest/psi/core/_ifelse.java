package coneforest.psi.core;
import coneforest.psi.*;

public class _ifelse extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj2=opstack.pop();
		PsiObject obj1=opstack.pop();
		PsiObject cond=opstack.pop();
		try
		{
			//int execlevel=interpreter.getExecutionStack().size();
			(((PsiBoolean)cond).booleanValue()? obj1: obj2).invoke(interpreter);
			//interpreter.handleExecutionStack(execlevel);
		}
		catch(ClassCastException e)
		{
			opstack.push(cond);
			opstack.push(obj1);
			opstack.push(obj2);
			interpreter.error("typecheck", this);
		}
	}
}
