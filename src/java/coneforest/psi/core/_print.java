package coneforest.psi.core;
import coneforest.psi.*;

public final class _print extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		((PsiWriter)interpreter.dictStack().load("stdout"))
			.psiWriteString((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
	}
}
