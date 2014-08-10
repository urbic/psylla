package coneforest.psi.core;
import coneforest.psi.*;

public class _readline extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject readable=opstack.pop();
		try
		{
			opstack.push(((PsiReadable)readable).psiReadLine());
		}
		catch(Exception e)
		{
			opstack.push(readable);
			interpreter.error(e, this);
		}
	}
}
