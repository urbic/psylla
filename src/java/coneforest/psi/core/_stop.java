package coneforest.psi.core;
import coneforest.psi.*;

public class _stop extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		interpreter.setStopFlag(true);
		if(interpreter.currentStopLevel()!=-1)
		{
			interpreter.getExecutionStack().setSize(interpreter.currentStopLevel());
		}
		else
		{
			// TODO?
			//System.out.println("STOP OUTSIDE STOPPED CONTEXT");
			System.exit(0);
		}
	}
}
