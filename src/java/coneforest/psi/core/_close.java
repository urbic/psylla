package coneforest.psi.core;
import coneforest.psi.*;

public class _close extends PsiOperator
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

		PsiObject closeable=opstack.pop();
		try
		{
			((PsiCloseable)closeable).psiClose();
		}
		catch(Exception e)
		{
			opstack.push(closeable);
			interpreter.error(e, this);
		}
	}
}
