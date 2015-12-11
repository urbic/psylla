package coneforest.psi.core;
import coneforest.psi.*;

public final class _warn extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiWriter stderror=(PsiWriter)interpreter.dictStack().load("stderr");
		stderror.psiWriteString((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
		stderror.psiFlush();
	}
}
