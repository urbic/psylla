package coneforest.psi.core;
import coneforest.psi.*;

public class _quit extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		System.exit(0);
	}
}
