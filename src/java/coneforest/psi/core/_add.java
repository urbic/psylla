package coneforest.psi.core;
import coneforest.psi.*;

public final class _add extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiAdditive)ops[0]).psiAdd((PsiAdditive)ops[1]));
	}
}
