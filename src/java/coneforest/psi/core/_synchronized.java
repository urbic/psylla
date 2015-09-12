package coneforest.psi.core;
import coneforest.psi.*;

public final class _synchronized extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject[] ops=opstack.popOperands(2);
		final PsiObject obj=ops[0];
		final PsiObject proc=ops[1];
		synchronized(obj)
		{
			final int loopLevel=interpreter.pushLoopLevel();
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
		}
	}
}
