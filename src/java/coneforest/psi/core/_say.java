package coneforest.psi.core;
import coneforest.psi.*;

public final class _say extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiWriter stdwriter=(PsiWriter)interpreter.dictStack().load("stdout");
		stdwriter.psiWriteString((PsiStringy)ostack.popOperands(1)[0]);
		stdwriter.psiWriteString((PsiStringy)interpreter.dictStack().load("eol"));
		stdwriter.psiFlush();
	}
}
