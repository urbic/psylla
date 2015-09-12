package coneforest.psi.core;
import coneforest.psi.*;

public final class _replicate extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiAppendable)ops[0]).psiReplicate((PsiInteger)ops[1]));
	}
}
