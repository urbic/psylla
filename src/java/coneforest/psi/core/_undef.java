package coneforest.psi.core;
import coneforest.psi.*;

public class _undef extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject key=opstack.pop();
		final PsiObject dictionarylike=opstack.pop();
		try
		{
			((PsiDictionarylike)dictionarylike).psiUndef((PsiStringlike)key);
		}
		catch(ClassCastException e)
		{
			opstack.push(dictionarylike);
			opstack.push(key);
			interpreter.error(e, this);
		}
	}
}
