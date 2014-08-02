package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _quit extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		System.exit(0);
	}
}
