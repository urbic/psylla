package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>abort</code> operator.
 */
public class _abort extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		interpreter.handleError("abort", this);
	}
}
