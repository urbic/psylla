package coneforest.psi.core;
import coneforest.psi.*;

public final class _hypot extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiNumeric)ops[0]).psiHypot((PsiNumeric)ops[1]));
	}
}
