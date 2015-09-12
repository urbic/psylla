package coneforest.psi.core;
import coneforest.psi.*;

public final class _pow extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiComplexNumeric)ops[0]).psiPow((PsiComplexNumeric)ops[1]));
	}
}
