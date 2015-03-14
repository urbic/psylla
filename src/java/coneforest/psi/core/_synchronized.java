package coneforest.psi.core;
import coneforest.psi.*;

public class _synchronized extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		PsiObject[] ops=opstack.popOperands(2);
		PsiObject obj=ops[0];
		PsiObject proc=ops[1];
		synchronized(obj)
		{
			final int loopLevel=interpreter.pushLoopLevel();
			proc.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
		}
	}
}
