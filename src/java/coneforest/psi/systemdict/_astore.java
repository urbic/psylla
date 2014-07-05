package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _astore extends PsiOperator
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
				int size=((PsiArray)array).size();
				if(opstack.size()<size)
					interpreter.error("stackunderflow");
				else
				{
					for(int i=size-1; i>=0; i--)
						((PsiArray)array).set(i, opstack.pop());
					opstack.push(array);
				}
			}
			else
				interpreter.error("typecheck");
		}
	}
}
