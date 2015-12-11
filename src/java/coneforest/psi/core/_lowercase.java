package coneforest.psi.core;
import coneforest.psi.*;

public final class _lowercase extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(((PsiStringy)ostack.popOperands(1)[0]).psiLowerCase());
	}
}
