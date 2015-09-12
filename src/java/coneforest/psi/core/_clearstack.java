package coneforest.psi.core;
import coneforest.psi.*;

public final class _clearstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getOperandStack().clear();
	}
}
