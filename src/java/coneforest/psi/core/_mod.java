package coneforest.psi.core;
import coneforest.psi.*;

public class _mod extends PsiOperator
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

		final PsiObject integer2=opstack.pop();
		final PsiObject integer1=opstack.pop();
		try
		{
			opstack.push(((PsiInteger)integer1).psiMod((PsiInteger)integer2));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(integer1);
			opstack.push(integer2);
			interpreter.handleError(e, this);
		}
	}
}
