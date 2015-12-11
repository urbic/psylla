package coneforest.psi.core;
import coneforest.psi.*;

public final class _issymlink extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(FileSystem.psiIsSymLink(((PsiStringy)ostack.popOperands(1)[0])));
	}
}
