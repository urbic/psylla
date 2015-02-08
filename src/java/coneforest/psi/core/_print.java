package coneforest.psi.core;
import coneforest.psi.*;

public class _print extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		((PsiWriter)interpreter.getDictionaryStack().load("stdout"))
			.psiWriteString((PsiStringlike)opstack.popOperands(1)[0]);
	}
}
