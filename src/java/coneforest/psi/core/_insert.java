package coneforest.psi.core;
import coneforest.psi.*;

public class _insert extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(3);
		((PsiArraylike)ops[0]).psiInsert((PsiInteger)ops[1], ops[2]);
	}
}
