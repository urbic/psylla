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
				if(result!=null)
					opstack.push(result);
				else
					interpreter.error("rangecheck");
			}
			else
				interpreter.error("typecheck");
		}
	}
}
