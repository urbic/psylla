package coneforest.psi.core;
import coneforest.psi.*;

public class _warn extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiWriter stdwriter=(PsiWriter)interpreter.getDictStack().load("stderr");
		stdwriter.psiWriteString((PsiStringlike)opstack.popOperands(1)[0]);
		stdwriter.psiFlush();
	}
}
