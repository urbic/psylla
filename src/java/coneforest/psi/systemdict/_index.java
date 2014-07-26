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
			int nValue=((PsiInteger)n).getValue().intValue();
			if(nValue<0)
				interpreter.error("rangecheck");
			else if(opstack.size()<nValue+1)
				interpreter.error("stackunderflow");
			else
				opstack.push(opstack.get(opstack.size()-nValue-1));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
	}
}
