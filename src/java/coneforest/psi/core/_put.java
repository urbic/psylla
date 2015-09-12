package coneforest.psi.core;
import coneforest.psi.*;

public final class _put extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(3);
		((PsiIndexed)ops[0]).psiPut(ops[1], ops[2]);
	}
}
