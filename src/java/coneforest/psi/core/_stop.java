package coneforest.psi.core;
import coneforest.psi.*;

public class _stop extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		if(interpreter.currentStopLevel()!=-1)
		{
			interpreter.setStopFlag(true);
			interpreter.getExecutionStack().setSize(interpreter.currentStopLevel());
		}
		else
		{
			// TODO?
		}
	}
}
