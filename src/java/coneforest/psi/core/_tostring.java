package coneforest.psi.core;
import coneforest.psi.*;

public final class _tostring extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiConvertableToString)opstack.popOperands(1)[0]).psiToString());
	}
}
