package coneforest.psi.core;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
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
			interpreter.getDictionaryStack().push((PsiDictionarylike)dict);
		}
		catch(ClassCastException e)
		{
			opstack.push(dict);
			interpreter.error(e, this);
		}
	}
}
