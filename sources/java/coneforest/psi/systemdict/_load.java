package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _load extends PSIOperator
{
	public String getName()	{ return "load"; }

	public void execute(PSIInterpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PSIObject key=opstack.pop();

			if(key instanceof PSIStringlike)
				opstack.push(interpreter.getDictionaryStack().load((PSIStringlike)key));
			else
				interpreter.error("typecheck");
			// TODO errors: invalidaccess, undefined
		}
	}
}
