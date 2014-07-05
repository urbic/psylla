package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _flush extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject file=opstack.pop();
			if(file instanceof PsiWriter)
			{
				try
				{
					((PsiWriter)file).flush();
				}
				catch(PsiException e)
				{
					interpreter.error(e.kind());
				}
			}
			else
				interpreter.error("typecheck");
		}
	}
}
