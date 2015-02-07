package coneforest.psi.core;
import coneforest.psi.*;

public class _putinterval extends PsiOperator
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

		final PsiObject iterable=opstack.pop();
		final PsiObject index=opstack.pop();
		final PsiObject arraylike=opstack.pop();
		try
		{
			((PsiArraylike)arraylike).psiPutInterval((PsiInteger)index, (PsiIterable)iterable);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(iterable);
			interpreter.handleError(e, this);
		}
	}
}
