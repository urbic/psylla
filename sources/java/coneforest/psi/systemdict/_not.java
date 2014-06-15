package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _not extends PSIOperator
{
	public String getName()	{ return "not"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PSIObject n=opstack.pop();

		if(n instanceof PSIBoolean)
			opstack.push(PSIBoolean.not((PSIBoolean)n));
		else if(n instanceof PSIInteger)
			opstack.push(PSIInteger.not((PSIInteger)n));
		else
		{
			opstack.push(n);
			interpreter.error("typecheck");
		}
	}
}
