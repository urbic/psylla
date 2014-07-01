package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject dict=opstack.pop();
			if(dict instanceof PsiDictionary)
				interpreter.getDictionaryStack().push((PsiDictionary)dict);
			else
				interpreter.error("typecheck");
		}
	}
}
