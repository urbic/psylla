package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _tan extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			switch(obj.getType())
			{
				case TYPE_INTEGER:
				case TYPE_REAL:
					opstack.push(new PSIReal(Math.tan(((Number)obj.getValue()).doubleValue())));
					break;
				default:
					opstack.push(obj);
					interpreter.error("typecheck");
			}
		}
	}
}
