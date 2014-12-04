package coneforest.psi.core;
import coneforest.psi.*;

public class _load extends PsiOperator
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

		final PsiObject key=opstack.pop();
		try
		{
			opstack.push(interpreter.getDictionaryStack().load((PsiStringlike)key));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(key);
			interpreter.error(e, this);
		}
	}
}
