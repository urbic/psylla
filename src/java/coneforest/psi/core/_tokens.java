package coneforest.psi.core;
import coneforest.psi.*;

public final class _tokens extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack ostack=interpreter.operandStack();
		ostack.ensureSize(1);

		final PsiObject stringy=ostack.pop();
		try
		{
			interpreter.interpretBraced(new PsiStringReader((PsiStringy)stringy));
		}
		catch(ClassCastException e)
		{
			ostack.push(stringy);
			interpreter.handleError(e, this);
		}
	}
}
