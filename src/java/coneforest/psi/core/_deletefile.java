package coneforest.psi.core;
import coneforest.psi.*;

public class _deletefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		Utility.psiDeleteFile((PsiStringlike)opstack.popOperands(1)[0]);
	}
}