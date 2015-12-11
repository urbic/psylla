package coneforest.psi.core;
import coneforest.psi.*;

public final class _countexecstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.operandStack().push(PsiInteger.valueOf(interpreter.executionStack().size()));
	}
}
