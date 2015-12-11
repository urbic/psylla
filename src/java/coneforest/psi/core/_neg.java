package coneforest.psi.core;
import coneforest.psi.*;

public final class _neg extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiAdditive)ostack.popOperands(1)[0]).psiNeg());
	}
}
