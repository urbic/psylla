package coneforest.psi.core;
import coneforest.psi.*;

public class _loop extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();

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
