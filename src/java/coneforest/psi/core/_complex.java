package coneforest.psi.core;
import coneforest.psi.*;

public final class _complex extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(new PsiComplex((PsiNumeric)ops[0], (PsiNumeric)ops[1]));
	}
}
