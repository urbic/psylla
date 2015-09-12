package coneforest.psi.core;
import coneforest.psi.*;

public final class _filemodifiedtime extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(FileSystem.psiFileModifiedTime((PsiStringy)opstack.popOperands(1)[0]));
	}
}
