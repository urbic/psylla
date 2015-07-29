package coneforest.psi.core;
import coneforest.psi.*;

public class _println extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiWriter stdwriter=(PsiWriter)interpreter.getDictStack().load("stdout");
		stdwriter.psiWriteString((PsiStringy)opstack.popOperands(1)[0]);
		stdwriter.psiWriteString((PsiStringy)interpreter.getDictStack().load("eol"));
	}
}
