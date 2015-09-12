package coneforest.psi.core;
import coneforest.psi.*;

public final class _stringwriter extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PsiStringWriter((PsiString)opstack.popOperands(1)[0]));
	}
}
