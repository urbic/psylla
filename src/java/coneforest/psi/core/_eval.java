package coneforest.psi.core;
import coneforest.psi.*;

public class _eval extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject evaluable=opstack.pop();
		try
		{
			((PsiEvaluable)evaluable).eval(interpreter);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(evaluable);
			interpreter.handleError(e, this);
		}
	}
}
