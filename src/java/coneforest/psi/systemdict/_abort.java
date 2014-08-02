package coneforest.psi.systemdict;
import coneforest.psi.*;

/**
 * The implementation of the <code>abort</code> operator.
 */
public class _abort extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		interpreter.error("abort", this);
	}
}
