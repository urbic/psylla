package coneforest.psi.core;
import coneforest.psi.*;

public class _currentdict extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push((PsiObject)interpreter.getCurrentDictionary());
	}
}
