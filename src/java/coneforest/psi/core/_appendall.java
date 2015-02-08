package coneforest.psi.core;
import coneforest.psi.*;

public class _appendall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiAppendable)ops[0]).psiAppendAll((PsiIterable)ops[1]);
	}
}
