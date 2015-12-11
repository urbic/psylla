package coneforest.psi.core;
import coneforest.psi.*;

public final class _undef extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final PsiObject[] ops=interpreter.operandStack().popOperands(2);
		((PsiDictlike)ops[0]).psiUndef((PsiStringy)ops[1]);
	}
}
