package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _quit extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		System.exit(0);
	}
}
