package coneforest.psi.core;
import coneforest.psi.*;

public class _flush extends PsiOperator
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

		final PsiObject flushable=opstack.pop();
		try
		{
			((PsiFlushable)flushable).psiFlush();
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(flushable);
			interpreter.handleError(e, this);
		}
	}
}
