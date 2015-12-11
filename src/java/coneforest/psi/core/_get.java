package coneforest.psi.core;
import coneforest.psi.*;

public final class _get extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiIndexed)ops[0]).psiGet(ops[1]));
	}
}
