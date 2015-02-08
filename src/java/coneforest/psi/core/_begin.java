package coneforest.psi.core;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		interpreter.getDictionaryStack().push((PsiDictionarylike)opstack.popOperands(1)[0]);
	}
}
