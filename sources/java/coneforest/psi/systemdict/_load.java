package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _load extends PSIOperator
{
	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject key=opstack.pop();

			if(key instanceof PSIStringlike)
			{
				PSIObject result=interpreter.getDictionaryStack().load((PSIStringlike)key);
				if(result==null)
					interpreter.error("undefined");
				else
					opstack.push(result);
			}
			else
				interpreter.error("typecheck");
			// TODO errors: invalidaccess, undefined
		}
	}
}
