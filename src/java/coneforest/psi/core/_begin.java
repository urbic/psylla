package coneforest.psi.core;
import coneforest.psi.*;

public final class _begin extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		interpreter.dictStack().push((PsiDictlike)ostack.popOperands(1)[0]);
	}
}
