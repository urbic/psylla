package coneforest.psi.core;
import coneforest.psi.*;

public final class _println extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiWriter stdwriter=(PsiWriter)interpreter.dictStack().load("stdout");
		stdwriter.psiWriteString((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
		stdwriter.psiWriteString((PsiStringy)interpreter.dictStack().load("eol"));
	}
}
