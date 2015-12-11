package coneforest.psi.core;
import coneforest.psi.*;

public final class _max extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(ops[((PsiScalar)ops[0]).psiGt((PsiScalar)ops[1]).booleanValue()? 0: 1]);
	}
}
