package coneforest.psi.core;
import coneforest.psi.*;

public final class _mul extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiArithmetic)ops[0]).psiMul((PsiArithmetic)ops[1]));
	}
}
