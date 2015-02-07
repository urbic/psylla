package coneforest.psi.core;
import coneforest.psi.*;

public class _xor extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject logical2=opstack.pop();
		final PsiObject logical1=opstack.pop();
		try
		{
			opstack.push(((PsiLogical)logical1).psiXor((PsiLogical)logical2));
		}
		catch(ClassCastException e)
		{
			opstack.push(logical1);
			opstack.push(logical2);
			interpreter.handleError(e, this);
		}
	}
}
