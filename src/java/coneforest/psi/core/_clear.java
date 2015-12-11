package coneforest.psi.core;
import coneforest.psi.*;

public final class _clear extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		((PsiClearable)ostack.popOperands(1)[0]).psiClear();
	}
}
