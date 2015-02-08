package coneforest.psi.core;
import coneforest.psi.*;

public class _array extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiArray());
	}
}
