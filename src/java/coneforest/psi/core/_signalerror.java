package coneforest.psi.core;
import coneforest.psi.*;

public class _signalerror extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
	{
	}

	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		try
		{
			final PsiObject[] ops=opstack.popOperands(2);
			interpreter.handleError(((PsiStringlike)ops[1]).getString(), ops[0]);
		}
		catch(ClassCastException e)
		{
			interpreter.handleError("typecheck", this);
		}
		catch(PsiException e)
		{
			interpreter.handleError(e.kind(), this);
		}
	}
}
