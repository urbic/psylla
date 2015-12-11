package coneforest.psi.core;
import coneforest.psi.*;

public final class _execstack extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		interpreter.operandStack().push(
			new PsiArray((java.util.ArrayList<PsiObject>)interpreter.executionStack().clone()));
	}
}
