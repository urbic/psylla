package coneforest.psi.core;
import coneforest.psi.*;

public class _retainall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiSetlike)ops[0]).psiRetainAll((PsiIterable)ops[1]);
	}
}
