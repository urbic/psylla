package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _known extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject key=opstack.pop();
			PSIObject dict=opstack.pop();

			if(key instanceof PSIStringlike && dict instanceof PSIDictionary)
				opstack.push(new PSIBoolean(((PSIDictionary)dict).containsKey((PSIStringlike)key)));
			else
				interpreter.error("typecheck");
			// TODO errors: invalidaccess, undefined
		}
	}
}
