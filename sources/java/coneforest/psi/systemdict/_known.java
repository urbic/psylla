package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _known extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject key=opstack.pop();
			PsiObject dict=opstack.pop();

			if(key instanceof PsiStringlike && dict instanceof PsiDictionary)
				opstack.push(new PsiBoolean(((PsiDictionary)dict).containsKey((PsiStringlike)key)));
			else
				interpreter.error("typecheck");
			// TODO errors: invalidaccess, undefined
		}
	}
}
