package coneforest.psi.core;
import coneforest.psi.*;

public final class _clear extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		((PsiClearable)opstack.popOperands(1)[0]).psiClear();
	}
}
