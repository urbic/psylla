package coneforest.psi.core;
import coneforest.psi.*;

public class _getinterval extends PsiOperator
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

		final PsiObject count=opstack.pop();
		final PsiObject index=opstack.pop();
		final PsiObject arraylike=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiArraylike)arraylike).psiGetInterval((PsiInteger)index, (PsiInteger)count));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(arraylike);
			opstack.push(index);
			opstack.push(count);
			interpreter.error(e, this);
		}
	}
}
