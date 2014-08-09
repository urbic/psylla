package coneforest.psi.core;
import coneforest.psi.*;

public class _sin extends PsiOperator
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

		PsiObject numeric=opstack.pop();
		try
		{
			opstack.push(((PsiNumeric)numeric).sin());
		}
		catch(Exception e)
		{
			opstack.push(numeric);
			interpreter.error(e, this);
		}
	}
}
