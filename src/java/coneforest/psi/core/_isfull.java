package coneforest.psi.core;
import coneforest.psi.*;

public final class _isfull extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiBounded)ostack.popOperands(1)[0]).psiIsFull());
	}
}
