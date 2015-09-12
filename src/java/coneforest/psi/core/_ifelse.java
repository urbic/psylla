package coneforest.psi.core;
import coneforest.psi.*;

public final class _ifelse extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(3);
		ops[((PsiBoolean)ops[0]).booleanValue()? 1: 2].invoke(interpreter);
	}
}
