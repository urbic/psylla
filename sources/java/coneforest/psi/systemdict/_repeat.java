package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _repeat extends PSIOperator
{
	public String getName()	{ return "repeat"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject obj=opstack.pop();
		PSIObject count=opstack.pop();

		if(count instanceof PSIInteger)
		{
			long countValue=((PSIInteger)count).getValue();
			if(countValue<0)
			{
				interpreter.error("rangecheck");
				return;
			}
			interpreter.pushLoopLevel();
			// TODO: reverse
			for(int i=0;
					i<countValue && !interpreter.getExitFlag();
					i++)
			{
					//int level=interpreter.getExecutionStack().size();
				//int currentLoopLevel=interpreter.getLoopLevel();
				obj.invoke(interpreter);
				//System.out.println("UUU "+currentLoopLevel);
				interpreter.handleExecutionStack(interpreter.currentLoopLevel());
				//interpreter.setLoopLevel(currentLoopLevel);
			}
			interpreter.setExitFlag(false);

		}
		else
			interpreter.error("typecheck");
	}
}
