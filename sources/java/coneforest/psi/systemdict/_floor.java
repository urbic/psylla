package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _floor extends PSIOperator
{
	public String getName()	{ return "floor"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()==0)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			if(obj instanceof PSINumeric)
				opstack.push(PSINumeric.floor((PSINumeric)obj));
			else
			{
				opstack.push(obj);
				interpreter.error("typecheck");
			}
		}
	}
}
