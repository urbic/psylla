package coneforest.psi.core;
import coneforest.psi.*;

public class _signalerror extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws ClassCastException, PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		interpreter.handleError(((PsiStringy)ops[1]).getString(), ops[0]);
	}
}
