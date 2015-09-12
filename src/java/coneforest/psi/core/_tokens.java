package coneforest.psi.core;
import coneforest.psi.*;

public final class _tokens extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		opstack.ensureSize(1);

		final PsiObject stringy=opstack.pop();
		try
		{
			interpreter.interpretBraced(new PsiStringReader((PsiStringy)stringy));
		}
		catch(ClassCastException e)
		{
			opstack.push(stringy);
			interpreter.handleError(e, this);
		}
	}
}
