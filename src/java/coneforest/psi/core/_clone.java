package coneforest.psi.core;
import coneforest.psi.*;

public final class _clone extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(ostack.popOperands(1)[0].psiClone());
	}
}
