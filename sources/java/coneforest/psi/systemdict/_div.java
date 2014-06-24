package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _div extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject y=opstack.pop();
		PSIObject x=opstack.pop();

		if(x instanceof PSINumeric && y instanceof PSINumeric)
		{
			PSIReal result=PSINumeric.ratio((PSINumeric)x, (PSINumeric)y);
			if(result.getValue().isInfinite())
			{
				opstack.push(x);
				opstack.push(y);
				interpreter.error("undefinedresult");
			}
			else
				opstack.push(result);
		}
		else
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck");
		}
	}
}
