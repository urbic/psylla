package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _div extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject arithmetic2=opstack.pop();
		PsiObject arithmetic1=opstack.pop();

		try
		{
			opstack.push((PsiObject)((PsiArithmetic)arithmetic1).div((PsiArithmetic)arithmetic2));
		}
		catch(ClassCastException e)
		{
			opstack.push(arithmetic1);
			opstack.push(arithmetic2);
			interpreter.error("typecheck");
		}

		/*
		if(x instanceof PsiNumeric && y instanceof PsiNumeric)
		{
			PsiReal result=PsiNumeric.ratio((PsiNumeric)x, (PsiNumeric)y);
			if(result.getValue().isInfinite())
			{
				opstack.push(x);
				opstack.push(y);
				interpreter.error("undefinedresult");
			}
			else
				opstack.push(result);
		}
		else
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck");
		}
		*/
	}
}
