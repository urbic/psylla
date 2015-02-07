package coneforest.psi.core;
import coneforest.psi.*;

public class _exit extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		if(interpreter.currentLoopLevel()==-1)
		{
			interpreter.handleError("invalidexit", this);
			return;
		}
		interpreter.getExecutionStack().setSize(interpreter.currentLoopLevel());
		interpreter.setExitFlag(true);
	}
}
