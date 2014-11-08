package coneforest.psi.core;
import coneforest.psi.*;

public class _get extends PsiOperator
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
		PsiObject indexed=opstack.pop();
		try
		{
			opstack.push(((PsiIndexed)indexed).psiGet(key));
		}
		catch(PsiException|ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			interpreter.error(e, this);
		}
	}
}
