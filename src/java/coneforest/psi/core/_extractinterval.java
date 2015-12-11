package coneforest.psi.core;
import coneforest.psi.*;

public final class _extractinterval extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(3);
		ostack.push(((PsiArraylike)ops[0]).psiExtractInterval((PsiInteger)ops[1], (PsiInteger)ops[2]));
	}
}
