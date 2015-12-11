package coneforest.psi.core;
import coneforest.psi.*;

public final class _countstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(PsiInteger.valueOf(ostack.size()));
	}
}
