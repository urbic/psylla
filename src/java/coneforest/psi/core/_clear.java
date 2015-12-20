package coneforest.psi.core;
import coneforest.psi.*;

public final class _clear extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiClearable)interpreter.operandStack().popOperands(1)[0]).psiClear();
	}
}
