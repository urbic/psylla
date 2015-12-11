package coneforest.psi.core;
import coneforest.psi.*;

public final class _halt extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		System.exit(((PsiInteger)interpreter.operandStack().popOperands(1)[0]).intValue());
	}
}
