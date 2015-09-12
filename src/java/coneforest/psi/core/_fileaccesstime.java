package coneforest.psi.core;
import coneforest.psi.*;

public final class _fileaccesstime extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(FileSystem.psiFileAccessTime((PsiStringy)opstack.popOperands(1)[0]));
	}
}
