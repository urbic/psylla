package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _writestring extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject string=opstack.pop();
			PsiObject writer=opstack.pop();
			if(writer instanceof PsiWriter && string instanceof PsiString)
			{
				try
				{
					((PsiWriter)writer).write((PsiString)string);
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
