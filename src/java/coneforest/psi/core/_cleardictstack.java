package coneforest.psi.core;
import coneforest.psi.*;

public final class _cleardictstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getDictStack().setSize(2);
	}
}
