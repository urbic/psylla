package coneforest.psi.core;
import coneforest.psi.*;

public final class _quit extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		interpreter.quit();
	}
}
