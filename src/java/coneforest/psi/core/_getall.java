package coneforest.psi.core;
import coneforest.psi.*;

public class _getall extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiIndexed)ops[0]).psiGetAll((PsiIterable)ops[1]));
	}
}