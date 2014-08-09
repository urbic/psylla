package coneforest.psi.core;
import coneforest.psi.*;

public class _mod extends PsiOperator
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

		PsiObject integer2=opstack.pop();
		PsiObject integer1=opstack.pop();
		try
		{
			opstack.push(((PsiInteger)integer1).psiMod((PsiInteger)integer2));
		}
		catch(Exception e)
		{
			opstack.push(integer1);
			opstack.push(integer2);
			interpreter.error(e, this);
		}
	}
}
