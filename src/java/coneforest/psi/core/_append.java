package coneforest.psi.core;
import coneforest.psi.*;

public class _append extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject obj=opstack.pop();
		PsiObject appendable=opstack.pop();
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
