package coneforest.psi.core;
import coneforest.psi.*;

public final class _capturegroupstart extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiMatcher)ops[0]).psiCaptureGroupStart((PsiInteger)ops[1]));
	}
}
