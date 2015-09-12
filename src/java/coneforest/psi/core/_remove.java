package coneforest.psi.core;
import coneforest.psi.*;

public final class _remove extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiSetlike)ops[0]).psiRemove(ops[1]);
	}
}
