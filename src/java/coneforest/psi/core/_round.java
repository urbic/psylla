package coneforest.psi.core;
import coneforest.psi.*;

public class _round extends PsiOperator
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
			opstack.push(((PsiNumeric)numeric).psiRound());
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric);
			interpreter.error(e, this);
		}
	}
}
