package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _where extends PsiOperator
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
				PsiDictionary dict=interpreter.getDictionaryStack().where((PsiStringlike)key);
				if(dict!=null)
				{
					opstack.push(dict);
					opstack.push(new PsiBoolean(true));
				}
				else
					opstack.push(new PsiBoolean(false));
			}
			else
				interpreter.error("typecheck");
		}
	}
}
