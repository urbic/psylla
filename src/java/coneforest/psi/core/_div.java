package coneforest.psi.core;
import coneforest.psi.*;

public final class _div extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiArithmetic)ops[0]).psiDiv((PsiArithmetic)ops[1]));
	}
}
