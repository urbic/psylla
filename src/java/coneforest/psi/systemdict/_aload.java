package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _aload extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
		}
		
		PsiObject array=opstack.pop();
		try
		{
			for(PsiObject obj: (PsiArray)array)
				opstack.push(obj);
			opstack.push(array);
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
	}
}
