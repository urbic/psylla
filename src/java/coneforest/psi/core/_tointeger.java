package coneforest.psi.core;
import coneforest.psi.*;

public final class _tointeger extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiConvertableToInteger)opstack.popOperands(1)[0]).psiToInteger());
	}
}
