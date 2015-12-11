package coneforest.psi.core;
import coneforest.psi.*;

public final class _sleep extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		PsiContext.psiSleep((PsiInteger)interpreter.operandStack().popOperands(1)[0]);
	}
}
