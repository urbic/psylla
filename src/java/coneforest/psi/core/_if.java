package coneforest.psi.core;
import coneforest.psi.*;

public class _if extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject obj=opstack.pop();
		final PsiObject cond=opstack.pop();
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
			interpreter.handleError(e, this);
		}
	}
}
