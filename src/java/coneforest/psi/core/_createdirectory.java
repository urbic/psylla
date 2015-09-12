package coneforest.psi.core;
import coneforest.psi.*;

public final class _createdirectory extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		FileSystem.psiCreateDirectory((PsiStringy)opstack.popOperands(1)[0]);
	}
}
