package coneforest.psi.core;
import coneforest.psi.*;

public final class _issamefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(FileSystem.psiIsSameFile((PsiStringy)ops[0], (PsiStringy)ops[1]));
	}
}
