package coneforest.psi.core;
import coneforest.psi.*;

public final class _sin extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiComplexNumeric)opstack.popOperands(1)[0]).psiSin());
	}
}
