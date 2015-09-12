package coneforest.psi.core;
import coneforest.psi.*;

public final class _stopped extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiObject obj=opstack.popOperands(1)[0];

		final int stopLevel=interpreter.pushStopLevel();
		obj.invoke(interpreter);
		interpreter.handleExecutionStack(stopLevel);
		opstack.push(PsiBoolean.valueOf(interpreter.getStopFlag()));
		interpreter.setStopFlag(false);
		interpreter.popStopLevel();
	}
}
