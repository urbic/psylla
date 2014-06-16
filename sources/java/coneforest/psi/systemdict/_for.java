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
				|| increment instanceof PSINumeric
				|| initial instanceof PSINumeric)
		{
			// TODO
			double limitValue=((Number)limit.getValue()).doubleValue();
			if(initial instanceof PSIInteger && increment instanceof PSIInteger)
			{
				// INTEGER
				long
					initialValue=(long)initial.getValue(),
					incrementValue=(long)increment.getValue();
				
				for(long i=initialValue; i<=limitValue; i+=incrementValue)
				{
					opstack.push(new PSIInteger(i));
					obj.execute(interpreter);
				}
			}
			else
			{
				// REAL
				double
					initialValue=((Number)initial.getValue()).doubleValue(),
					incrementValue=((Number)increment.getValue()).doubleValue();
				
				for(double i=initialValue; i<=limitValue; i+=incrementValue)
				{
					opstack.push(new PSIReal(i));
					obj.execute(interpreter);
				}
			}

		}
		else
			interpreter.error("typecheck");
	}
}
