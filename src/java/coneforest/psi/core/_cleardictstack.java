package coneforest.psi.core;
import coneforest.psi.*;

public class _cleardictstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
		interpreter.getDictionaryStack().setSize(2);
	}
}
