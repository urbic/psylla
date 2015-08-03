package coneforest.psi.core;
import coneforest.psi.*;

public class _grep extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiIterable)ops[0]).psiGrep((PsiProcedure)ops[1]));
	}
}
