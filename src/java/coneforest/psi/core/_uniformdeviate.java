package coneforest.psi.core;
import coneforest.psi.*;

public final class _uniformdeviate extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiRandom)ops[0]).psiUniformDeviate((PsiNumeric)ops[1]));
	}
}
