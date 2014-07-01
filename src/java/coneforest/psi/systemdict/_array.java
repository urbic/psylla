package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _array extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<1)
			interpreter.error("stackunderflow");

		PsiObject n=opstack.pop();
		if(n instanceof PsiInteger)
		{
			int nValue=((PsiInteger)n).getValue().intValue();
			if(nValue<0)
			{
				interpreter.error("rangecheck");
				return;
			}
			if(nValue>Integer.MAX_VALUE)
			{
				interpreter.error("limitcheck");
				return;
			}
			PsiArray array=new PsiArray();
			for(int i=0; i<nValue; i++)
				array.add(new PsiNull());
			opstack.push(array);
		}
		else
			interpreter.error("typecheck");
	}
}
