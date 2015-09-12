package coneforest.psi.core;
import coneforest.psi.*;

public final class _countstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(PsiInteger.valueOf(opstack.size()));
	}
}
