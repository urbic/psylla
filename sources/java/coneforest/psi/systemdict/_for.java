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
			int currentLoopLevel=interpreter.getLoopLevel();

			for(PSINumeric i=(PSINumeric)initial;
					PSINumeric.le(i, (PSINumeric)limit).getValue();
					i=PSINumeric.sum(i, (PSINumeric)increment))
			{
				opstack.push(i);
				obj.invoke(interpreter);
				interpreter.handleExecutionStack();
			}

			interpreter.setLoopLevel(currentLoopLevel);

		}
		else
			interpreter.error("typecheck");
	}
}
