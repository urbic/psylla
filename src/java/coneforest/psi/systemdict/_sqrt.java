package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _sqrt extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
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
			opstack.push(((PsiNumeric)numeric).sqrt());
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
