package coneforest.psi.core;
import coneforest.psi.*;

public final class _signalerror extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		interpreter.handleError(((PsiStringy)ops[1]).getString(), ops[0]);
	}
}
