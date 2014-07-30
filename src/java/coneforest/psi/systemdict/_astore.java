package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _astore extends PsiOperator
{
	@Override
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
			interpreter.error("stackunderflow", this);
		else
		{
			PsiObject count=opstack.pop();
			if(count instanceof PsiInteger)
			{
				int countValue=((PsiInteger)count).getValue().intValue();
				if(opstack.size()<countValue)
					interpreter.error("stackunderflow", this);
				else
				{
					PsiArray array=new PsiArray();
					while(--countValue>=0)
						((PsiArray)array).add(0, opstack.pop());
					opstack.push(array);
				}
			}
			else
				interpreter.error("typecheck", this);
		}
	}
}
