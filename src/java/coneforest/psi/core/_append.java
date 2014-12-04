package coneforest.psi.core;
import coneforest.psi.*;

public class _append extends PsiOperator
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

		final PsiObject obj=opstack.pop();
		final PsiObject appendable=opstack.pop();
		try
		{
			((PsiAppendable)appendable).psiAppend(obj);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(appendable);
			opstack.push(obj);
			interpreter.error(e, this);
		}
	}
}
