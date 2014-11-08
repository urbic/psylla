package coneforest.psi.core;
import coneforest.psi.*;

public class _setseed extends PsiOperator
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

		PsiObject integer=opstack.pop();
		PsiObject random=opstack.pop();
		try
		{
			((PsiRandom)random).psiSetSeed((PsiInteger)integer);
		}
		catch(ClassCastException e)
		{
			opstack.push(random);
			opstack.push(integer);
			interpreter.error(e, this);
		}
	}
}
