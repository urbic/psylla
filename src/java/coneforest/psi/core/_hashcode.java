package coneforest.psi.core;
import coneforest.psi.*;

public class _hashcode extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(opstack.popOperands(1)[0].psiHashCode());
	}
}
