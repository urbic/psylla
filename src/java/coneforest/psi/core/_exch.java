package coneforest.psi.core;
import coneforest.psi.*;

public final class _exch extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(ops[1]);
		ostack.push(ops[0]);
	}
}
