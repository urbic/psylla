package coneforest.psi.core;
import coneforest.psi.*;

public class _appendall extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject iterable=opstack.pop();
		final PsiObject appendable=opstack.pop();
		try
		{
			((PsiAppendable)appendable).psiAppendAll((PsiIterable)iterable);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(appendable);
			opstack.push(iterable);
			interpreter.handleError(e, this);
		}
	}
}
