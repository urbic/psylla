package coneforest.psi.core;
import coneforest.psi.*;

public class _slice extends PsiOperator
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

		PsiObject keys=opstack.pop();
		PsiObject indexed=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiIndexed)indexed).psiSlice((PsiIterable)keys));
		}
		catch(PsiException|ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(keys);
			interpreter.error(e, this);
		}
	}
}
