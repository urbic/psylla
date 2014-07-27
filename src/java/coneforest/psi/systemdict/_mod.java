package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _mod extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject y=opstack.pop();
		PsiObject x=opstack.pop();
		try
		{
			PsiInteger result=((PsiInteger)x).mod((PsiInteger)y);
			if(result==null)
			{
				interpreter.error("undefinedresult", this);
				return;
			}
			opstack.push(result);
		}
		catch(ClassCastException e)
		{
			opstack.push(x);
			opstack.push(y);
			interpreter.error("typecheck", this);
		}
	}
}
