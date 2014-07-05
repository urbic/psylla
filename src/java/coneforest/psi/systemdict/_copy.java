package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _copy extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PsiObject n=opstack.pop();
		if(n instanceof PsiInteger)
		{
			int i=((PsiInteger)n).getValue().intValue();
			if(i<0)
				interpreter.error("rangecheck");
			else if(opstack.size()<i)
				interpreter.error("stackunderflow");
			else
			{
				int opsize=opstack.size();
				for(int j=opsize-i; j<opsize; j++)
					opstack.push(opstack.get(j));
			}
		}
		// TODO other types of topmost operands
		else
			interpreter.error("typecheck");
	}
}
