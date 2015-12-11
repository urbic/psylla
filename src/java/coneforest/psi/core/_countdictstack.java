package coneforest.psi.core;
import coneforest.psi.*;

public final class _countdictstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.operandStack().push(PsiInteger.valueOf(interpreter.dictStack().size()));
	}
}
