package coneforest.psi.core;
import coneforest.psi.*;

public class _currentdict extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(interpreter.getCurrentDict());
	}
}
