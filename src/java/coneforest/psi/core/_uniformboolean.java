package coneforest.psi.core;
import coneforest.psi.*;

public class _uniformboolean extends PsiOperator
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

		final PsiObject random=opstack.pop();
		try
		{
			opstack.push(((PsiRandom)random).psiUniformBoolean());
		}
		catch(ClassCastException e)
		{
			opstack.push(random);
			interpreter.handleError(e, this);
		}
	}
}
