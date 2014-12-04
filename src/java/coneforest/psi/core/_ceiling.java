package coneforest.psi.core;
import coneforest.psi.*;

public class _ceiling extends PsiOperator
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
			opstack.push(((PsiNumeric)numeric).psiCeiling());
		}
		catch(ClassCastException e)
		{
			interpreter.error(e, this);
		}
	}
}
