package coneforest.psi.core;
import coneforest.psi.*;

public class _load extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(interpreter.getDictStack().load((PsiStringlike)opstack.popOperands(1)[0]));
	}
}
