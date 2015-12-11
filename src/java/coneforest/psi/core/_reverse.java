package coneforest.psi.core;
import coneforest.psi.*;

public final class _reverse extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiArraylike)ostack.popOperands(1)[0]).psiReverse());
	}
}
