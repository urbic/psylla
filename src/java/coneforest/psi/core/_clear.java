package coneforest.psi.core;
import coneforest.psi.*;

public class _clear extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		final PsiObject clearable=opstack.pop();
		try
		{
			((PsiClearable)clearable).psiClear();
		}
		catch(ClassCastException e)
		{
			opstack.push(clearable);
			interpreter.error(e, this);
		}
	}
}
