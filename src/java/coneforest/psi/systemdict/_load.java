package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _load extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject key=opstack.pop();

			if(key instanceof PsiStringlike)
			{
				try
				{
					opstack.push(interpreter.getDictionaryStack().load((PsiStringlike)key));
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
