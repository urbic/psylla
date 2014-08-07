package coneforest.psi.core;
import coneforest.psi.*;

public class _token extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
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
					interpreter.error(e.kind(), this);
				}
			}
			else
				interpreter.error("typecheck", this);
		}
	}
}
