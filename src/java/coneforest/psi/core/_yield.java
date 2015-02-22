package coneforest.psi.core;
import coneforest.psi.*;

public class _yield extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		Thread.currentThread().yield();
	}
}
