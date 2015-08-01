package coneforest.psi.core;
import coneforest.psi.*;

public class _executive extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		interpreter.repl();
	}
}
