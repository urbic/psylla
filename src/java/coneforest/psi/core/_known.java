package coneforest.psi.core;
import coneforest.psi.*;

public class _known extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiDictlike)ops[0]).psiKnown((PsiStringlike)ops[1]));
	}
}
