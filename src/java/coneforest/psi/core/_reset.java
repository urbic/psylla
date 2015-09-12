package coneforest.psi.core;
import coneforest.psi.*;

public final class _reset extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		((PsiResettable)opstack.popOperands(1)[0]).psiReset();
	}
}
