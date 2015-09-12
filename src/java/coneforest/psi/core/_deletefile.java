package coneforest.psi.core;
import coneforest.psi.*;

public final class _deletefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		FileSystem.psiDeleteFile((PsiStringy)opstack.popOperands(1)[0]);
	}
}
