package coneforest.psi.core;
import coneforest.psi.*;

public class _mul extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiArithmetic)ops[0]).psiMul((PsiArithmetic)ops[1]));
	}
}
