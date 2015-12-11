package coneforest.psi.core;
import coneforest.psi.*;

public final class _exec extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		interpreter.operandStack().popOperands(1)[0].invoke(interpreter);
	}
}
