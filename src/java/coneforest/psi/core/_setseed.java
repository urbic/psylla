package coneforest.psi.core;
import coneforest.psi.*;

public final class _setseed extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiRandom)ops[0]).psiSetSeed((PsiInteger)ops[1]);
	}
}
