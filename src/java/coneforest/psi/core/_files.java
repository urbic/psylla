package coneforest.psi.core;
import coneforest.psi.*;

public class _files extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(FileSystem.psiFiles((PsiStringy)opstack.popOperands(1)[0]));
	}
}
