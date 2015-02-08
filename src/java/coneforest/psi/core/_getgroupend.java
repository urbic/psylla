package coneforest.psi.core;
import coneforest.psi.*;

public class _getgroupend extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiMatcher)ops[0]).psiGetGroupEnd((PsiInteger)ops[1]));
	}
}
