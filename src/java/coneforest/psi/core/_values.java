package coneforest.psi.core;
import coneforest.psi.*;

public class _values extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject dictionarylike=opstack.pop();
		try
		{
			opstack.push(((PsiDictionarylike)dictionarylike).psiValues());
		}
		catch(ClassCastException e)
		{
			opstack.push(dictionarylike);
			interpreter.error(e, this);
		}
	}
}
