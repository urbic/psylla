package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _token extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow");
		else
		{
			PsiObject reader=opstack.pop();
			if(reader instanceof PsiReader)
			{
				try
				{
					PsiObject obj=interpreter.getPsiObject((PsiReader)reader);
					if(obj==null)
						opstack.push(new PsiBoolean(false));
					else
					{
						opstack.push(obj);
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
