package coneforest.psi.core;
import coneforest.psi.*;

public final class _intersects extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiSetlike)ops[0]).psiIntersects((PsiSetlike)ops[1]));
	}
}
