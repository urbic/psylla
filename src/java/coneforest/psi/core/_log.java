package coneforest.psi.core;
import coneforest.psi.*;

public class _log extends PsiOperator
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
			opstack.push(((PsiNumeric)numeric).log());
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric);
			interpreter.error("typecheck", this);
		}

		/*
		if(obj instanceof PsiNumeric)
		{
			PsiReal result=PsiNumeric.sqrt((PsiNumeric)obj);
			if(result.getValue().isNaN())
				interpreter.error("rangecheck");
			else
				opstack.push(result);
		}
		else
			interpreter.error("typecheck");
		*/
	}
}