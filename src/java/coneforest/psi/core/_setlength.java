package coneforest.psi.core;
import coneforest.psi.*;

public class _setlength extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiObject[] ops=opstack.popOperands(2);
		((PsiArraylike)ops[0]).psiSetLength((PsiInteger)ops[1]);
	}
}
