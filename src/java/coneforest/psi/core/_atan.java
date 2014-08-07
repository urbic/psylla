package coneforest.psi.core;
import coneforest.psi.*;

public class _atan extends PsiOperator
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

		try
		{
			opstack.push(((PsiNumeric)opstack.pop()).atan());
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
