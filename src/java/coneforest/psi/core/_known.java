package coneforest.psi.core;
import coneforest.psi.*;

public class _known extends PsiOperator
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
		final PsiObject dict=opstack.pop();
		try
		{
			opstack.push(((PsiDictionarylike)dict).psiKnown((PsiStringlike)key));
		}
		catch(ClassCastException e)
		{
			opstack.push(dict);
			opstack.push(key);
			interpreter.error(e, this);
		}
	}
}
