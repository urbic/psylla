package coneforest.psi.core;
import coneforest.psi.*;

public class _flush extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		((PsiFlushable)opstack.popOperands(1)[0]).psiFlush();
	}
}
