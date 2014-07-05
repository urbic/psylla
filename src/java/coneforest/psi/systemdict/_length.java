package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _length extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject obj=opstack.pop();

			if(obj instanceof PsiStringlike)
				opstack.push(new PsiInteger(((PsiStringlike)obj).getValue().length()));
			else if(obj instanceof PsiArray)
				opstack.push(new PsiInteger(((PsiArray)obj).size()));
			else if(obj instanceof PsiDictionary)
				opstack.push(new PsiInteger(((PsiDictionary)obj).size()));
			else
			{
				opstack.push(obj);
				interpreter.error("typecheck");
			}
		}
	}
}