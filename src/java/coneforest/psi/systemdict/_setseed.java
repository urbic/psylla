package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _setseed extends PsiOperator
{
	public void execute(Interpreter interpreter)
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
			interpreter.error("typecheck", this);
		}
	}
}
