package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _flush extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}

		PsiObject file=opstack.pop();
		try
		{
			((PsiFlushable)file).flush();
		}
		catch(ClassCastException e)
		{
			opstack.push(file);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(file);
			interpreter.error(e.kind(), this);
		}
	}
}
