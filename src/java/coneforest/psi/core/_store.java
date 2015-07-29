package coneforest.psi.core;
import coneforest.psi.*;

public class _store extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		interpreter.getDictStack().store((PsiStringlike)ops[0], ops[1]);
	}
}