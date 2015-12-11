package coneforest.psi.core;
import coneforest.psi.*;

public final class _pow extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiComplexNumeric)ops[0]).psiPow((PsiComplexNumeric)ops[1]));
	}
}
