package coneforest.psi.core;
import coneforest.psi.*;

public final class _prettyprint extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		System.out.println(interpreter.operandStack().popOperands(1)[0].toSyntaxString());
	}
}
