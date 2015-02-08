package coneforest.psi.core;
import coneforest.psi.*;

public class _eq extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(ops[0].psiEq(ops[1]));
	}
}
