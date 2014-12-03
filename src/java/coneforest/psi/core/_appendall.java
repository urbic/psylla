package coneforest.psi.core;
import coneforest.psi.*;

public class _appendall extends PsiOperator
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

		PsiObject iterable=opstack.pop();
		PsiObject appendable=opstack.pop();
		try
		{
			((PsiAppendable)appendable).psiAppendAll((PsiIterable)iterable);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(appendable);
			opstack.push(iterable);
			interpreter.error(e, this);
		}
	}
}
