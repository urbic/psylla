package coneforest.psi.core;
import coneforest.psi.*;

public final class _inunicodeblock extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(((PsiInteger)ops[0]).psiInUnicodeBlock((PsiStringy)ops[1]));
	}
}
