package coneforest.psi.core;
import coneforest.psi.*;

public final class _type extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(ostack.popOperands(1)[0].psiType());
	}
}
