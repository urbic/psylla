package coneforest.psi.core;
import coneforest.psi.*;

public class _lowercase extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiStringlike)opstack.popOperands(1)[0]).psiLowerCase());
	}
}