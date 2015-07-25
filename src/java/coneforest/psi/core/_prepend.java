package coneforest.psi.core;
import coneforest.psi.*;

public class _prepend extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiPrependable)ops[0]).psiPrepend(ops[1]);
	}
}
