package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _def extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject obj=opstack.pop();
			PSIObject key=opstack.pop();

			if(key instanceof PSIStringlike)
			{
				PSIDictionary currentdict=interpreter.getDictionaryStack().peek();
				currentdict.put((PSIStringlike)key, obj);
			}
			else
				interpreter.error("typecheck");
		}
	}
}
