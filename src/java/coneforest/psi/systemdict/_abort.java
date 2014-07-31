package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _abort extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		interpreter.error("abort", this);
	}
}
