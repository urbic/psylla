package coneforest.psi.core;
import coneforest.psi.*;

public class _readstring extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		PsiString string=((PsiReadable)ops[0]).psiReadString((PsiInteger)ops[1]);
		opstack.push(string);
		opstack.push(string.psiLength().psiEq((PsiInteger)ops[1]));
	}
}
