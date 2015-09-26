package coneforest.psi.core;
import coneforest.psi.*;

public final class _isinstance extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiType)ops[0]).psiIsInstance(ops[1]));
	}
}
