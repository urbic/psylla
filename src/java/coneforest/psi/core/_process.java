package coneforest.psi.core;
import coneforest.psi.*;

public final class _process extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PsiProcess((PsiDictlike)opstack.popOperands(1)[0]));
	}
}
