package coneforest.psi.core;
import coneforest.psi.*;

public class _loop extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject obj=opstack.pop();

		int looplevel=interpreter.pushLoopLevel();
		while(!interpreter.getExitFlag())
		{
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(looplevel);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
	}
}
