package coneforest.psi.core;
import coneforest.psi.*;

public class _flush extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject flushable=opstack.pop();
		try
		{
			((PsiFlushable)flushable).psiFlush();
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(flushable);
			interpreter.error(e, this);
		}
	}
}
