package coneforest.psi.core;
import coneforest.psi.*;

public class _not extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject logical=opstack.pop();
		try
		{
			opstack.push(((PsiLogical)logical).psiNot());
		}
		catch(ClassCastException e)
		{
			opstack.push(logical);
			interpreter.handleError(e, this);
		}
	}
}
