package coneforest.psi.core;
import coneforest.psi.*;

public final class _gt extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiScalar)ops[0]).psiGt((PsiScalar)ops[1]));
	}
}
