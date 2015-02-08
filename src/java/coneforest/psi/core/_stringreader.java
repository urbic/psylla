package coneforest.psi.core;
import coneforest.psi.*;

public class _stringreader extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.push(new PsiStringReader((PsiStringlike)opstack.popOperands(1)[0]));
	}
}
