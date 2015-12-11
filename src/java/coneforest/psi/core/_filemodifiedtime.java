package coneforest.psi.core;
import coneforest.psi.*;

public final class _filemodifiedtime extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(FileSystem.psiFileModifiedTime((PsiStringy)ostack.popOperands(1)[0]));
	}
}
