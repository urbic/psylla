package coneforest.psi.core;
import coneforest.psi.*;

public class _loop extends PsiOperator
{
	@Override
	public void action(final Interpreter interpreter)
		throws PsiException
	{
		final OperandStack opstack=interpreter.getOperandStack();
		final PsiObject obj=opstack.popOperands(1)[0];

		int loopLevel=interpreter.pushLoopLevel();
		while(true)
		{
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(loopLevel);
			if(interpreter.getStopFlag() || interpreter.getExitFlag())
				break;
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
