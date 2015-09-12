package coneforest.psi.core;
import coneforest.psi.*;

public final class _filereader extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PsiFileReader((PsiStringy)opstack.popOperands(1)[0]));
	}
}
