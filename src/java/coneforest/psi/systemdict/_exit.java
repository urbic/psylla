package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exit extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		//interpreter.show("EXIT BEFORE");
		if(interpreter.currentLoopLevel()==-1)
		{
			interpreter.error("invalidexit", this);
			return;
		}
		interpreter.getExecutionStack().setSize(interpreter.currentLoopLevel());
		interpreter.setExitFlag(true);
		//interpreter.show("EXIT AFTER");
	}
}
