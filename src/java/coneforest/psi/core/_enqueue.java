package coneforest.psi.core;
import coneforest.psi.*;

public final class _enqueue extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiQueuelike)ops[0]).psiEnqueue(ops[1]);
	}
}
