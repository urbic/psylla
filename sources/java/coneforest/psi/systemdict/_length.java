package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _length extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();

			if(obj instanceof PSIStringlike)
				opstack.push(new PSIInteger(((PSIStringlike)obj).getValue().length()));
			else if(obj instanceof PSIArray)
				opstack.push(new PSIInteger(((PSIArray)obj).size()));
			else if(obj instanceof PSIDictionary)
				opstack.push(new PSIInteger(((PSIDictionary)obj).size()));
			else
			{
				opstack.push(obj);
				interpreter.error("typecheck");
			}
		}
	}
}
