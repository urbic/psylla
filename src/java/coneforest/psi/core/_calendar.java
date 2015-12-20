package coneforest.psi.core;
import coneforest.psi.*;

public final class _calendar extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(Time.psiCalendar((PsiInteger)ostack.popOperands(1)[0]));
	}
}
