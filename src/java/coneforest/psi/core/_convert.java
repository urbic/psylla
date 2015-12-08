package coneforest.psi.core;
import coneforest.psi.*;

public final class _convert extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(ops[0].psiConvert((PsiType)ops[1]));
	}
}
