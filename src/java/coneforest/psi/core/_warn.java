package coneforest.psi.core;
import coneforest.psi.*;

public final class _warn extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiWriter stderror=(PsiWriter)interpreter.getDictStack().load("stderr");
		stderror.psiWriteString((PsiStringy)opstack.popOperands(1)[0]);
		stderror.psiFlush();
	}
}
