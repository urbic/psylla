package coneforest.psi.core;
import coneforest.psi.*;

public final class _createdirectory extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		FileSystem.psiCreateDirectory((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
	}
}
