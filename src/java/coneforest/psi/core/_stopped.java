package coneforest.psi.core;
import coneforest.psi.*;

public class _stopped extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final int stopLevel=interpreter.pushStopLevel();
		opstack.pop().invoke(interpreter);
		interpreter.handleExecutionStack(stopLevel);
		opstack.push(new PsiBoolean(interpreter.getStopFlag()));
		interpreter.setStopFlag(false);
		interpreter.popStopLevel();
	}
}
