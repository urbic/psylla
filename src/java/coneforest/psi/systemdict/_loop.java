package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _loop extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();

		//interpreter.show("LOOP BEFORE");
		int looplevel=interpreter.pushLoopLevel();
		//interpreter.show("LOOP MIDDLE");
		while(!interpreter.getExitFlag())
		{
			obj.invoke(interpreter);
			interpreter.handleExecutionStack(looplevel);
		}
		interpreter.popLoopLevel();
		interpreter.setExitFlag(false);
		//interpreter.show("LOOP AFTER");
	}
}
