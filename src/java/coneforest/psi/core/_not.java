package coneforest.psi.core;
import coneforest.psi.*;

public class _not extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiLogical)opstack.popOperands(1)[0]).psiNot());
	}
}
