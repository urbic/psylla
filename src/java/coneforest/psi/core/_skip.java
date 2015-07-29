package coneforest.psi.core;
import coneforest.psi.*;

public class _skip extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiReadable)ops[0]).psiSkip((PsiInteger)ops[1]);
	}
}
