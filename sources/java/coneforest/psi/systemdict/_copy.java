package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _copy extends PSIOperator
{
	public String getName()	{ return "copy"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PSIObject n=opstack.pop();
		if(n instanceof PSIInteger)
		{
			int i=((PSIInteger)n).getValue().intValue();
			if(i<0)
				interpreter.error("rangecheck");
			else if(opstack.size()<i)
				interpreter.error("stackunderflow");
			else
			{
				int opsize=opstack.size();
				for(int j=opsize-i; j<opsize; j++)
					opstack.push(opstack.elementAt(j));
			}
		}
		// TODO other types of topmost operands
		else
			interpreter.error("typecheck");
	}
}
