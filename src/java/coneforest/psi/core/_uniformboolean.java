package coneforest.psi.core;
import coneforest.psi.*;

public class _uniformboolean extends PsiOperator
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

		PsiObject random=opstack.pop();
		try
		{
			opstack.push(((PsiRandom)random).psiUniformBoolean());
		}
		catch(Exception e)
		{
			opstack.push(random);
			interpreter.error(e, this);
		}
	}
}
