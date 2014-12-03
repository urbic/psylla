package coneforest.psi.core;
import coneforest.psi.*;

public class _uniformdeviate extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject numeric=opstack.pop();
		PsiObject random=opstack.pop();
		try
		{
			opstack.push(((PsiRandom)random).psiUniformDeviate((PsiNumeric)numeric));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(random);
			opstack.push(numeric);
			interpreter.error(e, this);
		}
	}
}
