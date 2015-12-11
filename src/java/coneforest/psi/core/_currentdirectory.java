package coneforest.psi.core;
import coneforest.psi.*;

public final class _currentdirectory extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		interpreter.operandStack().push(FileSystem.psiCurrentDirectory());
	}
}
