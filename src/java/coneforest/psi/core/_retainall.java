package coneforest.psi.core;
import coneforest.psi.*;

public class _retainall extends PsiOperator
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

		PsiObject iterable=opstack.pop();
		PsiObject setlike=opstack.pop();
		try
		{
			((PsiSetlike)setlike).psiRetainAll((PsiIterable)iterable);
		}
		catch(ClassCastException e)
		{
			opstack.push(setlike);
			opstack.push(iterable);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(setlike);
			opstack.push(iterable);
			interpreter.error(e.kind(), this);
		}
	}
}