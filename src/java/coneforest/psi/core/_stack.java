package coneforest.psi.core;
import coneforest.psi.*;

public final class _stack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.push(new PsiArray((java.util.ArrayList<PsiObject>)ostack.clone()));
	}
}
