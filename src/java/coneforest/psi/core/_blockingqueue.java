package coneforest.psi.core;
import coneforest.psi.*;

public final class _blockingqueue extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(new PsiBlockingQueue((PsiInteger)ostack.popOperands(1)[0]));
	}
}
