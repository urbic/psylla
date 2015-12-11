package coneforest.psi.core;
import coneforest.psi.*;

public final class _ceiling extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiNumeric)ostack.popOperands(1)[0]).psiCeiling());
	}
}
