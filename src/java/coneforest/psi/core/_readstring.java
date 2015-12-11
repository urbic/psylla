package coneforest.psi.core;
import coneforest.psi.*;

public final class _readstring extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		PsiString string=((PsiReadable)ops[0]).psiReadString((PsiInteger)ops[1]);
		ostack.push(string);
		ostack.push(string.psiLength().psiEq((PsiInteger)ops[1]));
	}
}
