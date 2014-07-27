package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}
		
		PsiObject dict=opstack.pop();
		try
		{
			interpreter.getDictionaryStack().push((PsiDictionary)dict);
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
