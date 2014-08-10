package coneforest.psi.core;
import coneforest.psi.*;

public class _rcheck extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject composite=opstack.pop();
		try
		{
			opstack.push(new PsiBoolean(((PsiLengthy)composite).isReadable()));
		}
		catch(Exception e)
		{
			opstack.push(composite);
			interpreter.error(e, this);
		}
	}
}
