package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _close extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PsiObject file=opstack.pop();
		if(file instanceof PsiCloseable)
		{
			try
			{
				((PsiCloseable)file).close();
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
