package coneforest.psi.core;
import coneforest.psi.*;

public class _delete extends PsiOperator
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
		final PsiObject indexed=opstack.pop();
		try
		{
			opstack.push(((PsiIndexed)indexed).psiDelete(key));
		}
		catch(PsiException|ClassCastException e)
		{
			opstack.push(indexed);
			opstack.push(key);
			interpreter.error(e, this);
		}
	}
}
