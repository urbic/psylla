package coneforest.psi.core;
import coneforest.psi.*;

public class _toreal extends PsiOperator
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

		final PsiObject convertable=opstack.pop();
		try
		{
			opstack.push(((PsiConvertableToReal)convertable).psiToReal());
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(convertable);
			interpreter.error(e, this);
		}
	}
}
