package coneforest.psi.core;
import coneforest.psi.*;

public class _replicate extends PsiOperator
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

		final PsiObject count=opstack.pop();
		final PsiObject appendable=opstack.pop();
		try
		{
			opstack.push(((PsiAppendable)appendable).psiReplicate((PsiInteger)count));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(appendable);
			opstack.push(count);
			interpreter.handleError(e, this);
		}
	}
}
