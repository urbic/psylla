package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _stopped extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
		else
		{
			int stoplevel=interpreter.pushStopLevel();
			opstack.pop().invoke(interpreter);
			interpreter.handleExecutionStack(stoplevel);
			interpreter.getOperandStack().push(new PsiBoolean(interpreter.getStopFlag()));
			interpreter.setStopFlag(false);
			interpreter.popStopLevel();
		}
	}
}
