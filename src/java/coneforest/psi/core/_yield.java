package coneforest.psi.core;
import coneforest.psi.*;

public final class _yield extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		Thread.yield();
	}
}
