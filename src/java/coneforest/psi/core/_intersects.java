package coneforest.psi.core;
import coneforest.psi.*;

public class _intersects extends PsiOperator
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

		final PsiObject setlike2=opstack.pop();
		final PsiObject setlike1=opstack.pop();
		try
		{
			opstack.push(((PsiSetlike)setlike1).psiIntersects((PsiSetlike)setlike2));
		}
		catch(ClassCastException e)
		{
			opstack.push(setlike1);
			opstack.push(setlike2);
			interpreter.handleError(e, this);
		}
	}
}
