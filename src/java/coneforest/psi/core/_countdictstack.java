package coneforest.psi.core;
import coneforest.psi.*;

public class _countdictstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getOperandStack().push(new PsiInteger(interpreter.getDictionaryStack().size()));
	}
}
