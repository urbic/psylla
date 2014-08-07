package coneforest.psi.core;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
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
