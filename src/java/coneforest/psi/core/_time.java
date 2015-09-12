package coneforest.psi.core;
import coneforest.psi.*;

public final class _time extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		//interpreter.getOperandStack().push(new PsiInteger((new java.util.Date()).getTime()));
		interpreter.getOperandStack().push(new PsiInteger(System.currentTimeMillis()));
	}
}
