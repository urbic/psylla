package coneforest.psi.core;
import coneforest.psi.*;

public final class _symlink extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		FileSystem.psiSymLink((PsiStringy)ops[0], (PsiStringy)ops[1]);
	}
}
