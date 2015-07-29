package coneforest.psi.core;
import coneforest.psi.*;

public class _unite extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiArray)ops[0]).psiUnite((PsiStringy)ops[1]));
	}
}
