package coneforest.psi.core;
import coneforest.psi.*;

public final class _pop extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.ensureSize(1);
		opstack.pop();
	}
}
