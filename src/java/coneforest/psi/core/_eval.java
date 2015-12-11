package coneforest.psi.core;
import coneforest.psi.*;

public final class _eval extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiEvaluable)interpreter.operandStack().popOperands(1)[0]).eval(interpreter);
	}
}
