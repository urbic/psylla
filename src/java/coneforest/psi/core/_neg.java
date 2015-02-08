package coneforest.psi.core;
import coneforest.psi.*;

public class _neg extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(((PsiArithmetic)opstack.popOperands(1)[0]).psiNeg());
	}
}
