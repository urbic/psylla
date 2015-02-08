package coneforest.psi.core;
import coneforest.psi.*;

public class _split extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(((PsiString)ops[0]).psiSplit((PsiRegExp)ops[1]));
	}
}
