package coneforest.psi.core;
import coneforest.psi.*;

public final class _setlength extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiArraylike)ops[0]).psiSetLength((PsiInteger)ops[1]);
	}
}
