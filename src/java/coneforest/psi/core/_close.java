package coneforest.psi.core;
import coneforest.psi.*;

public class _close extends PsiOperator
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

		final PsiObject closeable=opstack.pop();
		try
		{
			((PsiCloseable)closeable).psiClose();
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(closeable);
			interpreter.handleError(e, this);
		}
	}
}
