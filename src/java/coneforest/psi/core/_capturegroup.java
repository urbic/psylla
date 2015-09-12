package coneforest.psi.core;
import coneforest.psi.*;

public final class _capturegroup extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiObject key=ops[1];
		opstack.push(((PsiMatcher)ops[0]).psiCaptureGroup(ops[1]));
	}
}
