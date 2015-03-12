package coneforest.psi.core;
import coneforest.psi.*;

public class _createdirectory extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		Utility.psiCreateDirectory((PsiStringlike)opstack.popOperands(1)[0]);
	}
}
