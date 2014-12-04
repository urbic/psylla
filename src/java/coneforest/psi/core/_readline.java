package coneforest.psi.core;
import coneforest.psi.*;

public class _readline extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject readable=opstack.pop();
		try
		{
			opstack.push(((PsiReadable)readable).psiReadLine());
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(readable);
			interpreter.error(e, this);
		}
	}
}
