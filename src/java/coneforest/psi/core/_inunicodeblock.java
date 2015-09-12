package coneforest.psi.core;
import coneforest.psi.*;

public final class _inunicodeblock extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiInteger)ops[0]).psiInUnicodeBlock((PsiStringy)ops[1]));
	}
}
