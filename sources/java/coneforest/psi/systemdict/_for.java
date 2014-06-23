package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _for extends PSIOperator
{
	public String getName()	{ return "for"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<4)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject obj=opstack.pop();
		PSIObject limit=opstack.pop();
		PSIObject increment=opstack.pop();
		PSIObject initial=opstack.pop();

		if(limit instanceof PSINumeric
				&& increment instanceof PSINumeric
				&& initial instanceof PSINumeric)
		{

			interpreter.pushLoopLevel();
			// TODO: reverse
			for(PSINumeric i=(PSINumeric)initial;
					PSINumeric.le(i, (PSINumeric)limit).getValue() && !interpreter.getExitFlag();
					i=PSINumeric.sum(i, (PSINumeric)increment))
			{
				opstack.push(i);
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
