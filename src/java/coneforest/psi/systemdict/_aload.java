package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _aload extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject array=opstack.pop();

			if(array instanceof PsiArray)
			{
				for(PsiObject obj: (PsiArray)array)
					opstack.push(obj);
				opstack.push(array);
			}
			else
				interpreter.error("typecheck");
		}
	}
}
