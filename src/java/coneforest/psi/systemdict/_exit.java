package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exit extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		//interpreter.show("EXIT BEFORE");
		if(interpreter.currentLoopLevel()==-1)
		{
			interpreter.error("invalidexit");
			return;
		}
		interpreter.getExecutionStack().setSize(interpreter.currentLoopLevel());
		interpreter.setExitFlag(true);
		//interpreter.show("EXIT AFTER");
	}
}
