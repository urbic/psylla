package coneforest.psi.core;
import coneforest.psi.*;

public final class _flush extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiFlushable)interpreter.operandStack().popOperands(1)[0]).psiFlush();
	}
}
