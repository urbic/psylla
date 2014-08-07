package coneforest.psi.core;
import coneforest.psi.*;

public class _contains extends PsiOperator
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

		PsiObject obj=opstack.pop();
		PsiObject setlike=opstack.pop();
		try
		{
			opstack.push(((PsiSetlike)setlike).psiContains(obj));
		}
		catch(ClassCastException e)
		{
			opstack.push(setlike);
			opstack.push(obj);
			interpreter.error("typecheck", this);
		}
	}
}
