package coneforest.psi.core;
import coneforest.psi.*;

public class _give extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiQueuelike)ops[0]).psiGive(ops[1]);
	}
}