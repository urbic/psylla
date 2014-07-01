package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _def extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();
			PsiObject key=opstack.pop();

			if(key instanceof PsiStringlike)
			{
				PsiDictionary currentdict=interpreter.getDictionaryStack().peek();
				currentdict.put((PsiStringlike)key, obj);
			}
			else
				interpreter.error("typecheck");
		}
	}
}
