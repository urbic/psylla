package coneforest.psi.core;
import coneforest.psi.*;

public class _deleteinterval extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(3);
		opstack.push(((PsiArraylike)ops[0]).psiDeleteInterval((PsiInteger)ops[1], (PsiInteger)ops[2]));
	}
}
