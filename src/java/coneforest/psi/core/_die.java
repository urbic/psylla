package coneforest.psi.core;
import coneforest.psi.*;

public class _die extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiWriter stderror=(PsiWriter)interpreter.getDictStack().load("stderr");
		stderror.psiWriteString((PsiStringlike)opstack.popOperands(1)[0]);
		stderror.psiFlush();
		interpreter.quit();
	}
}
