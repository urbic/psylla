package coneforest.psi.core;
import coneforest.psi.*;

public class _sleep extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		Utility.psiSleep((PsiNumeric)opstack.popOperands(1)[0]);
	}
}
