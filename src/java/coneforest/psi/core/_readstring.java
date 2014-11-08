package coneforest.psi.core;
import coneforest.psi.*;

public class _readstring extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject count=opstack.pop();
		PsiObject readable=opstack.pop();
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
			interpreter.error(e, this);
		}
	}
}
