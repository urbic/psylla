package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _sqrt extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.peek();
			if(obj instanceof PSINumeric)
			{
				PSIReal result=PSINumeric.sqrt((PSINumeric)obj);
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
