package coneforest.psi.core;
import coneforest.psi.*;

public final class _intersects extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiSetlike)ops[0]).psiIntersects((PsiSetlike)ops[1]));
	}
}
