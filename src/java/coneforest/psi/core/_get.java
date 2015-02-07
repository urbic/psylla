package coneforest.psi.core;
import coneforest.psi.*;

public class _get extends PsiOperator
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

		final PsiObject key=opstack.pop();
		final PsiObject indexed=opstack.pop();
		try
		{
			opstack.push(((PsiIndexed)indexed).psiGet(key));
		}
		catch(PsiException|ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			interpreter.handleError(e, this);
		}
	}
}
