package coneforest.psi.core;
import coneforest.psi.*;

public final class _copyfile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		FileSystem.psiCopyFile((PsiStringy)ops[0], (PsiStringy)ops[1]);
	}
}