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
		final PsiInteger oGroupStart=((PsiMatcher)ops[0]).psiCaptureGroupStart((PsiInteger)ops[1]);
		if(oGroupStart!=null)
			ostack.push(oGroupStart);
		ostack.push(PsiBoolean.valueOf(oGroupStart!=null));
	}
}
