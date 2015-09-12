package coneforest.psi.core;
import coneforest.psi.*;

public final class _filesize extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(FileSystem.psiFileSize((PsiStringy)opstack.popOperands(1)[0]));
	}
}
