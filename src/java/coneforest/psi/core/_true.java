package coneforest.psi.core;
import coneforest.psi.*;

public class _true extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(PsiBoolean.TRUE);
	}
}
