package coneforest.psi.core;
import coneforest.psi.*;

public final class _isfile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(FileSystem.psiIsFile(((PsiStringy)ostack.popOperands(1)[0])));
	}
}
