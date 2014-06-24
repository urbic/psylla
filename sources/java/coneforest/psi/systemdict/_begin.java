package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _begin extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject dict=opstack.pop();
			if(dict instanceof PSIDictionary)
				interpreter.getDictionaryStack().push((PSIDictionary)dict);
			else
				interpreter.error("typecheck");
		}
	}
}
