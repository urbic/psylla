package coneforest.psi.core;
import coneforest.psi.*;

public final class _capturegroup extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		final PsiString oGroup=((PsiMatcher)ops[0]).psiCaptureGroup(ops[1]);
		if(oGroup!=null)
			ostack.push(oGroup);
		ostack.push(PsiBoolean.valueOf(oGroup!=null));
	}
}
