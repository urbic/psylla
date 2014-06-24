package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _sqrt extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.peek();
			switch(obj.getType())
			{
				case TYPE_INTEGER:
				case TYPE_REAL:
					double value=((Number)obj.getValue()).doubleValue();
					if(value>=0.)
					{
						opstack.pop();
						opstack.push(new PSIReal(Math.sqrt(value)));
					}
					else
						interpreter.error("rangecheck");
					break;
				default:
					interpreter.error("typecheck");
			}
		}
	}
}
