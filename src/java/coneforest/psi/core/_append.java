package coneforest.psi.core;
import coneforest.psi.*;

public final class _append extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiAppendable)ops[0]).psiAppend(ops[1]);
	}
}
