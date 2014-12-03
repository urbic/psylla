package coneforest.psi.core;
import coneforest.psi.*;

public class _def extends PsiOperator
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

		PsiObject obj=opstack.pop();
		PsiObject key=opstack.pop();
		try
		{
			interpreter.getDictionaryStack().peek().psiPut((PsiStringlike)key, obj);
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(key);
			opstack.push(obj);
			interpreter.error(e, this);
		}
	}
}
