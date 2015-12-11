package coneforest.psi.core;
import coneforest.psi.*;

public final class _normaldeviate extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiRandom)ops[0]).psiNormalDeviate((PsiNumeric)ops[1]));
	}
}
