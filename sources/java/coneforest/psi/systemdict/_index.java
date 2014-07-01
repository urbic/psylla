package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _index extends PsiOperator
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
				opstack.push(opstack.elementAt(opstack.size()-i-1));
		}
		else
			interpreter.error("typecheck");
	}
}
