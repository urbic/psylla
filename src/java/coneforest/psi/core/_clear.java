package coneforest.psi.core;
import coneforest.psi.*;

public class _clear extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject clearable=opstack.pop();
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
