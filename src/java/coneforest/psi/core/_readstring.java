package coneforest.psi.core;
import coneforest.psi.*;

public class _readstring extends PsiOperator
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
		final PsiObject readable=opstack.pop();
		try
		{
			PsiString string=((PsiReadable)readable).psiReadString((PsiInteger)count);
			opstack.push(string);
			opstack.push(string.psiLength().psiEq((PsiInteger)count));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(count);
			opstack.push(readable);
			interpreter.handleError(e, this);
		}
	}
}
