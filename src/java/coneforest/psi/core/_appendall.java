package coneforest.psi.core;
import coneforest.psi.*;

public final class _appendall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiAppendable)ops[0]).psiAppendAll((PsiIterable)ops[1]);
	}
}
