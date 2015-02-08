package coneforest.psi.core;
import coneforest.psi.*;

public class _isempty extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiLengthy)opstack.popOperands(1)[0]).psiIsEmpty());
	}
}
