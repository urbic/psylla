package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _log extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			if(obj instanceof PSINumeric)
			{
				PSIReal result=PSINumeric.log((PSINumeric)obj);
				if(result.getValue().isNaN() || result.getValue().isInfinite())
					interpreter.error("rangecheck");
				else
					opstack.push(result);
			}
			else
				interpreter.error("typecheck");
		}
	}
}
