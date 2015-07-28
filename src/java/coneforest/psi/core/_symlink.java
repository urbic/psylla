package coneforest.psi.core;
import coneforest.psi.*;

public class _symlink extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		FileSystem.psiSymLink((PsiStringlike)ops[0], (PsiStringlike)ops[1]);
	}
}
