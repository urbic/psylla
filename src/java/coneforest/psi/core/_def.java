package coneforest.psi.core;
import coneforest.psi.*;

public class _def extends PsiOperator
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

		final PsiObject obj=opstack.pop();
		final PsiObject key=opstack.pop();
		try
		{
			interpreter.getDictionaryStack().peek().psiPut((PsiStringlike)key, obj);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(key);
			opstack.push(obj);
			interpreter.handleError(e, this);
		}
	}
}
