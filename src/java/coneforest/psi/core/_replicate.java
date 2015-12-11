package coneforest.psi.core;
import coneforest.psi.*;

public final class _replicate extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiAppendable)ops[0]).psiReplicate((PsiInteger)ops[1]));
	}
}
