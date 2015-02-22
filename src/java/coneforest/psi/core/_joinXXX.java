package coneforest.psi.core;
import coneforest.psi.*;

public class _join extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiString)ops[0]).psiJoin((PsiArraylike)ops[1]));
	}
}