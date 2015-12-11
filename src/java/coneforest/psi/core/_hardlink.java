package coneforest.psi.core;
import coneforest.psi.*;

public final class _hardlink extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		FileSystem.psiHardLink((PsiStringy)ops[0], (PsiStringy)ops[1]);
	}
}
