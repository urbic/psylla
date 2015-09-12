package coneforest.psi.core;
import coneforest.psi.*;

public final class _prettyprint extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		System.out.println(opstack.popOperands(1)[0].toSyntaxString());
	}
}
