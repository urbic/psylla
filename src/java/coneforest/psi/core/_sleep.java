package coneforest.psi.core;
import coneforest.psi.*;

public class _sleep extends PsiOperator
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

		final PsiObject numeric=opstack.pop();
		try
		{
			Utility.psiSleep((PsiNumeric)numeric);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(numeric);
			interpreter.error(e, this);
		}
	}
}
