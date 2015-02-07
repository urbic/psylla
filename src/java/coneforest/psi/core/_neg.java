package coneforest.psi.core;
import coneforest.psi.*;

public class _neg extends PsiOperator
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

		final PsiObject arihmetic=opstack.pop();
		try
		{
			opstack.push(((PsiArithmetic)arihmetic).psiNeg());
		}
		catch(ClassCastException e)
		{
			opstack.push(arihmetic);
			interpreter.handleError(e, this);
		}
	}
}
