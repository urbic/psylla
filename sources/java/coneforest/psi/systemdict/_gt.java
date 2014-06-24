package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _gt extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject obj2=opstack.pop();
		PSIObject obj1=opstack.pop();

		if(obj1 instanceof PSINumeric && obj2 instanceof PSINumeric)
			opstack.push(PSINumeric.gt((PSINumeric)obj1, (PSINumeric)obj2));
		else if(obj1 instanceof PSIStringlike && obj2 instanceof PSIStringlike)
			opstack.push(PSIStringlike.gt((PSIStringlike)obj1, (PSIStringlike)obj2));
		else
			interpreter.error("typecheck");
	}
}