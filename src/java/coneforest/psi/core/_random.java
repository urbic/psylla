package coneforest.psi.core;
import coneforest.psi.*;

public final class _random extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.operandStack().push(new PsiRandom());
	}
}
