package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _close extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject file=opstack.pop();
		try
		{
			((PsiCloseable)file).close();
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			interpreter.error(e.kind());
		}
	}
}
