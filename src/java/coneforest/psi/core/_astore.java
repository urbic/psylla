package coneforest.psi.core;
import coneforest.psi.*;

public class _astore extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
		else
		{
			final PsiObject count=opstack.pop();
			if(count instanceof PsiInteger)
			{
				int countValue=((PsiInteger)count).intValue();
				if(opstack.size()<countValue)
					interpreter.error("stackunderflow", this);
				else
				{
					PsiArray array=new PsiArray();
					while(--countValue>=0)
						((PsiArray)array).psiAppend(opstack.pop());
					opstack.push(array);
				}
			}
			else
				interpreter.error("typecheck", this);
		}
	}
}
