package coneforest.psi.core;
import coneforest.psi.*;

public final class _sin extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiComplexNumeric)ostack.popOperands(1)[0]).psiSin());
	}
}
