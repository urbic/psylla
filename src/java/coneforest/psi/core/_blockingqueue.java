package coneforest.psi.core;
import coneforest.psi.*;

public class _blockingqueue extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PsiBlockingQueue((PsiInteger)opstack.popOperands(1)[0]));
	}
}
