package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _sub extends PSIOperator
{
	public String getName()	{ return "sub"; }

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
			opstack.push(PSINumeric.difference((PSINumeric)x, (PSINumeric)y));
		else
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck");
		}
	}
}
