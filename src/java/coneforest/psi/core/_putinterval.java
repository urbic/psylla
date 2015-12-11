package coneforest.psi.core;
import coneforest.psi.*;

public final class _putinterval extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(3);
		((PsiArraylike)ops[0]).psiPutInterval((PsiInteger)ops[1], (PsiIterable)ops[2]);
	}
}
