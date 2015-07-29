package coneforest.psi.core;
import coneforest.psi.*;

public class _issymlink extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(FileSystem.psiIsSymLink(((PsiStringy)opstack.popOperands(1)[0])));
	}
}
