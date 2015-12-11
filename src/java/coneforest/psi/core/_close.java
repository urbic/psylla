package coneforest.psi.core;
import coneforest.psi.*;

public final class _close extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiCloseable)interpreter.operandStack().popOperands(1)[0]).psiClose();
	}
}
