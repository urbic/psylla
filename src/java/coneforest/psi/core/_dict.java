package coneforest.psi.core;
import coneforest.psi.*;

public class _dict extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiDictionary());
	}
}
