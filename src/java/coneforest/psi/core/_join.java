package coneforest.psi.core;
import coneforest.psi.*;

public class _join extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(1);
		final PsiContext context=(PsiContext)ops[0];
		context.join();
	}
}
