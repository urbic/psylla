package coneforest.psi.core;
import coneforest.psi.*;

public final class _reset extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiResettable)interpreter.operandStack().popOperands(1)[0]).psiReset();
	}
}
