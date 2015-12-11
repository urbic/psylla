package coneforest.psi.core;
import coneforest.psi.*;

public final class _retainall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiSetlike)ops[0]).psiRetainAll((PsiIterable)ops[1]);
	}
}
