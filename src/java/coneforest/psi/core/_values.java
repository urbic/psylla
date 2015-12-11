package coneforest.psi.core;
import coneforest.psi.*;

public final class _values extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.operandStack();
		opstack.push(((PsiIndexed)opstack.popOperands(1)[0]).psiValues());
	}
}
