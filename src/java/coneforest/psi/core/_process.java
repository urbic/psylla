package coneforest.psi.core;
import coneforest.psi.*;

public final class _process extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(new PsiProcess((PsiDictlike)ostack.popOperands(1)[0]));
	}
}
