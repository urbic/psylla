package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _index extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}

		PsiObject n=opstack.pop();
		try
		{
			int i=((PsiInteger)n).getValue().intValue();
			if(i<0)
				interpreter.error("rangecheck");
			else if(opstack.size()<i)
				interpreter.error("stackunderflow");
			else
				opstack.push(opstack.get(opstack.size()-i-1));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
		/*
		if(n instanceof PsiInteger)
		{
			int i=((PsiInteger)n).getValue().intValue();
			if(i<0)
				interpreter.error("rangecheck");
			else if(opstack.size()<i)
				interpreter.error("stackunderflow");
			else
				opstack.push(opstack.get(opstack.size()-i-1));
		}
		else
			interpreter.error("typecheck");
		*/
	}
}
