package coneforest.psi.core;
import coneforest.psi.*;

public class _def extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		interpreter.getDictionaryStack().peek().psiPut((PsiStringlike)ops[0], ops[1]);
	}
}
