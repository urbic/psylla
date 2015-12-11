package coneforest.psi.core;
import coneforest.psi.*;

public final class _write extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiWritable)ops[0]).psiWrite((PsiInteger)ops[1]);
	}
}
