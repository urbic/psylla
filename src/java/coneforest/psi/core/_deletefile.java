package coneforest.psi.core;
import coneforest.psi.*;

public final class _deletefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		FileSystem.psiDeleteFile((PsiStringy)interpreter.operandStack().popOperands(1)[0]);
	}
}
