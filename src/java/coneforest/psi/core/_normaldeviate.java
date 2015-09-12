package coneforest.psi.core;
import coneforest.psi.*;

public final class _normaldeviate extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiRandom)ops[0]).psiNormalDeviate((PsiNumeric)ops[1]));
	}
}
