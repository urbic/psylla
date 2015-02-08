package coneforest.psi.core;
import coneforest.psi.*;

public class _cmp extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiScalar)ops[0]).psiCmp((PsiScalar)ops[1]));
	}
}
