package coneforest.psi.core;
import coneforest.psi.*;

/**
 * The implementation of the <code>abort</code> operator.
 */
public class _abort extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		throw new PsiException("abort");
	}
}
