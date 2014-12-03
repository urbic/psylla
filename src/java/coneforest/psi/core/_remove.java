package coneforest.psi.core;
import coneforest.psi.*;

public class _remove extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();
		PsiObject setlike=opstack.pop();
		try
		{
			((PsiSetlike)setlike).psiRemove(obj);
		}
		catch(ClassCastException e)
		{
			opstack.push(setlike);
			opstack.push(obj);
			interpreter.error(e, this);
		}
	}
}
