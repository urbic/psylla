package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exit extends PSIOperator
{
	public String getName()	{ return "exit"; }

	public void execute(PSIInterpreter interpreter)
	{
		//System.out.println("EXIT: EXECLEVEL BEFORE="+interpreter.getExecutionStack().size());
		//interpreter.getExecutionStack().setSize(interpreter.getLoopLevel());
		//System.out.println("EXIT: EXECLEVEL AFTER="+interpreter.getExecutionStack().size());
		//System.out.println("EXIT: POPLOOPLEVEL="+interpreter.popLoopLevel());
		if(interpreter.currentLoopLevel()==-1)
		{
			interpreter.error("invalidexit");
			return;
		}
		interpreter.getExecutionStack().setSize(interpreter.currentLoopLevel());
		interpreter.setExitFlag(true);
	}
}
