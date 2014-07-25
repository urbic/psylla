package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _mod extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject y=opstack.pop();
		PsiObject x=opstack.pop();

		try
		{
			//PsiInteger result=PsiInteger.mod((PsiInteger)x, (PsiInteger)y);
			PsiInteger result=((PsiInteger)x).mod((PsiInteger)y);
			if(result==null)
			{
				interpreter.error("undefinedresult");
				return;
			}
			opstack.push(result);
		}
		catch(ClassCastException e)
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck");
		}
		/*
		if(x instanceof PsiInteger && y instanceof PsiInteger)
		{
			PsiInteger result=PsiInteger.mod((PsiInteger)x, (PsiInteger)y);
			if(result==null)
			{
				interpreter.error("undefinedresult");
				return;
			}
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
