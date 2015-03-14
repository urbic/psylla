package coneforest.psi.core;
import coneforest.psi.*;

public class _halt extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		System.exit(((PsiInteger)interpreter.getOperandStack().popOperands(1)[0]).intValue());
	}
}
