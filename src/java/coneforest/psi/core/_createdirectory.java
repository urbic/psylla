package coneforest.psi.core;
import coneforest.psi.*;

public class _createdirectory extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		FileSystem.psiCreateDirectory((PsiStringlike)opstack.popOperands(1)[0]);
	}
}
