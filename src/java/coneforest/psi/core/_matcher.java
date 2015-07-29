package coneforest.psi.core;
import coneforest.psi.*;

public class _matcher extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(new PsiMatcher((PsiStringy)ops[0], (PsiRegExp)ops[1]));
	}
}
