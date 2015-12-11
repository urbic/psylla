package coneforest.psi.core;
import coneforest.psi.*;

public final class _notify extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiCondition)interpreter.operandStack().popOperands(1)[0]).psiNotify();
	}
}
