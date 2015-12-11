package coneforest.psi.core;
import coneforest.psi.*;

public final class _readlink extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(FileSystem.psiReadLink((PsiStringy)ostack.popOperands(1)[0]));
	}
}
