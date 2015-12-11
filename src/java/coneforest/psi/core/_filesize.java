package coneforest.psi.core;
import coneforest.psi.*;

public final class _filesize extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(FileSystem.psiFileSize((PsiStringy)ostack.popOperands(1)[0]));
	}
}
