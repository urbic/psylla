package coneforest.psi.core;
import coneforest.psi.*;

public final class _ifelse extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(3);
		ops[((PsiBoolean)ops[0]).booleanValue()? 1: 2].invoke(interpreter);
	}
}
