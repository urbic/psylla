package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _array extends PSIOperator
{
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
			PSIArray array=new PSIArray();
			for(int i=0; i<nValue; i++)
				array.add(new PSINull());
			opstack.push(array);
		}
		else
			interpreter.error("typecheck");
	}
}
