package coneforest.psi.core;
import coneforest.psi.*;

public final class _capturegroupend extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		final PsiInteger oGroupEnd=((PsiMatcher)ops[0]).psiCaptureGroupEnd((PsiInteger)ops[1]);
		if(oGroupEnd!=null)
			ostack.push(oGroupEnd);
		ostack.push(PsiBoolean.valueOf(oGroupEnd!=null));
	}
}
