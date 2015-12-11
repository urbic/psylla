package coneforest.psi.core;
import coneforest.psi.*;

public final class _matcher extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(new PsiMatcher((PsiStringy)ops[0], (PsiRegExp)ops[1]));
	}
}
