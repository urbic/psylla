package coneforest.psi.core;
import coneforest.psi.*;

public class _issamefile extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		opstack.push(FileSystem.psiIsSameFile((PsiStringlike)ops[0], (PsiStringlike)ops[1]));
	}
}
