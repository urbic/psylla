package coneforest.psi.core;
import coneforest.psi.*;

public final class _and extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiLogical)ops[0]).psiAnd((PsiLogical)ops[1]));
	}
}
