package coneforest.psi.core;
import coneforest.psi.*;

public class _insert extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject obj=opstack.pop();
		final PsiObject index=opstack.pop();
		final PsiObject arraylike=opstack.pop();
		try
		{
			((PsiArraylike)arraylike).psiInsert((PsiInteger)index, obj);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(obj);
			interpreter.error(e, this);
		}
	}
}
