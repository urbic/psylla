package coneforest.psi.core;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		interpreter.getDictStack().push((PsiDictlike)opstack.popOperands(1)[0]);
	}
}
