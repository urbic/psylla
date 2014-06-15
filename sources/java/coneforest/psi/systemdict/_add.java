package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _add extends PSIOperator
{
	public String getName()	{ return "add"; }

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
			opstack.push(PSINumeric.sum((PSINumeric)x, (PSINumeric)y));
		else
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck");
		}
	}
}
