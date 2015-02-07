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
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject readable=opstack.pop();
		try
		{
			PsiStringlike eol=(PsiStringlike)interpreter.getDictionaryStack().load("eol");
			PsiString string=((PsiReadable)readable).psiReadLine(eol);
			if(string.length()>0)
				opstack.push(string);
			opstack.push(new PsiBoolean(string.length()>0));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(readable);
			interpreter.handleError(e, this);
		}
	}
}
