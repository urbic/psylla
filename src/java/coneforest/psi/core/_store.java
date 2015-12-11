package coneforest.psi.core;
import coneforest.psi.*;

public final class _store extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		interpreter.dictStack().store((PsiStringy)ops[0], ops[1]);
	}
}
