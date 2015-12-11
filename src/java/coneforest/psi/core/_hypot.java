package coneforest.psi.core;
import coneforest.psi.*;

public final class _hypot extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiNumeric)ops[0]).psiHypot((PsiNumeric)ops[1]));
	}
}
