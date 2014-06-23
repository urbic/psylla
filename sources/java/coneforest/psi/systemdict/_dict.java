package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _dict extends PSIOperator
{
	public String getName()	{ return "dict"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PSIObject n=opstack.pop();
		if(n instanceof PSIInteger)
		{
			int nValue=((PSIInteger)n).getValue().intValue();
			if(nValue<0)
			{
				interpreter.error("rangecheck");
				return;
			}
			if(nValue>Integer.MAX_VALUE)
			{
				interpreter.error("limitcheck");
				return;
			}
			opstack.push(new PSIDictionary());
		}
		else
			interpreter.error("typecheck");
	}
}
