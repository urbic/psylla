package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _roll extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<2)
			interpreter.error("stackunderflow");

		PSIObject j=opstack.pop();
		PSIObject n=opstack.pop();
		if(n instanceof PSIInteger && j instanceof PSIInteger)
		{
			int nValue=((PSIInteger)n).getValue().intValue();
			int jValue=((PSIInteger)j).getValue().intValue();
			int opstackSize=opstack.size();
			if(nValue<0)
			{
				interpreter.error("rangecheck");
				return;
			}
			else if(opstackSize<nValue)
			{
				interpreter.error("stackunderflow");
				return;
			}
			else
			{
				while(jValue<0)
					jValue+=nValue;
				jValue%=nValue;
				for(int i=0; i<jValue; i++)
					opstack.add(opstackSize-nValue, opstack.pop());
			}
		}
		else
			interpreter.error("typecheck");
	}
}
