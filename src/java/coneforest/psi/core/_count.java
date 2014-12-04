package coneforest.psi.core;
import coneforest.psi.*;

public class _count extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PsiInteger(opstack.size()));
	}
}
