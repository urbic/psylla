package coneforest.psi.core;
import coneforest.psi.*;

public class _close extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		((PsiCloseable)opstack.popOperands(1)[0]).psiClose();
	}
}
