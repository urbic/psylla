package coneforest.psi.core;
import coneforest.psi.*;

public class _put extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject obj=opstack.pop();
		final PsiObject key=opstack.pop();
		final PsiObject indexed=opstack.pop();
		try
		{
			((PsiIndexed)indexed).psiPut(key, obj);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			opstack.push(obj);
			interpreter.handleError(e, this);
		}
	}
}
