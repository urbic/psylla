package coneforest.psi.core;
import coneforest.psi.*;

public class _retainall extends PsiOperator
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

		final PsiObject iterable=opstack.pop();
		final PsiObject setlike=opstack.pop();
		try
		{
			((PsiSetlike)setlike).psiRetainAll((PsiIterable)iterable);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(setlike);
			opstack.push(iterable);
			interpreter.handleError(e, this);
		}
	}
}
