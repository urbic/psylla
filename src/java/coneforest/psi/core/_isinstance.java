package coneforest.psi.core;
import coneforest.psi.*;

public final class _isinstance extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiType)ops[0]).psiIsInstance(ops[1]));
	}
}
