package coneforest.psi.core;
import coneforest.psi.*;

public class _undef extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject key=opstack.pop();
		PsiObject dictionarylike=opstack.pop();
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
