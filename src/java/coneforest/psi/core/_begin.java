package coneforest.psi.core;
import coneforest.psi.*;

public class _begin extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject dict=opstack.pop();
		try
		{
			interpreter.getDictionaryStack().push((PsiDictionarylike)dict);
		}
		catch(ClassCastException e)
		{
			opstack.push(dict);
			interpreter.handleError(e, this);
		}
	}
}
