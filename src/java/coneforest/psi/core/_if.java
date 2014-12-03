package coneforest.psi.core;
import coneforest.psi.*;

public class _if extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
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
			if(((PsiBoolean)cond).booleanValue())
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
			interpreter.error(e, this);
		}
	}
}
