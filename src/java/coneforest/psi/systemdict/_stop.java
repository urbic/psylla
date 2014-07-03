package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _stop extends PsiOperator
{
	public void execute(Interpreter interpreter)
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
