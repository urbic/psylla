package coneforest.psi.core;
import coneforest.psi.*;

public class _slice extends PsiOperator
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

		final PsiObject keys=opstack.pop();
		final PsiObject indexed=opstack.pop();
		try
		{
			opstack.push(((PsiIndexed)indexed).psiSlice((PsiIterable)keys));
		}
		catch(PsiException|ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(keys);
			interpreter.handleError(e, this);
		}
	}
}
