package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _read extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject file=opstack.pop();
			if(file instanceof PsiReader)
			{
				try
				{
					int character=((PsiReader)file).read();
					if(character==-1)
						opstack.push(new PsiBoolean(false));
					else
					{
						opstack.push(new PsiInteger(character));
						opstack.push(new PsiBoolean(true));
					}
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
