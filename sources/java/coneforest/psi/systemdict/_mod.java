package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _mod extends PSIOperator
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

		if(x instanceof PSIInteger && y instanceof PSIInteger)
		{
			PSIInteger result=PSIInteger.mod((PSIInteger)x, (PSIInteger)y);
			if(result==null)
			{
				interpreter.error("undefinedresult");
				return;
			}
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
