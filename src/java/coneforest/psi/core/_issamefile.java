package coneforest.psi.core;
import coneforest.psi.*;

public final class _issamefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		final PsiObject[] ops=ostack.popOperands(2);
		ostack.push(FileSystem.psiIsSameFile((PsiStringy)ops[0], (PsiStringy)ops[1]));
	}
}
