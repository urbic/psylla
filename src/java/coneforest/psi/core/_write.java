package coneforest.psi.core;
import coneforest.psi.*;

public class _write extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		((PsiWritable)ops[0]).psiWrite((PsiInteger)ops[1]);
	}
}