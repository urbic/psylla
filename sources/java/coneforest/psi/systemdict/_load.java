package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _load extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject key=opstack.pop();

			if(key instanceof PsiStringlike)
			{
				PsiObject result=interpreter.getDictionaryStack().load((PsiStringlike)key);
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
