package coneforest.psi.core;
import coneforest.psi.*;

public class _false extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(PsiBoolean.FALSE);
	}
}
