package coneforest.psi.core;
import coneforest.psi.*;

public final class _filereader extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(new PsiFileReader((PsiStringy)ostack.popOperands(1)[0]));
	}
}
