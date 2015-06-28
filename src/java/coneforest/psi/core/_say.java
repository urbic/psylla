package coneforest.psi.core;
import coneforest.psi.*;

public class _say extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiWriter stdwriter=(PsiWriter)interpreter.getDictStack().load("stdout");
		stdwriter.psiWriteString((PsiStringlike)opstack.popOperands(1)[0]);
		stdwriter.psiWriteString((PsiStringlike)interpreter.getDictStack().load("eol"));
		stdwriter.psiFlush();
	}
}
