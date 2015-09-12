package coneforest.psi.core;
import coneforest.psi.*;

public final class _mod extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiInteger)ops[0]).psiMod((PsiInteger)ops[1]));
	}
}
