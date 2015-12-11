package coneforest.psi.core;
import coneforest.psi.*;

public final class _if extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		if(((PsiBoolean)ops[0]).booleanValue())
			ops[1].invoke(interpreter);
	}
}
