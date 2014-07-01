package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _roll extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		
		if(opstack.size()<2)
			interpreter.error("stackunderflow");

		PsiObject j=opstack.pop();
		PsiObject n=opstack.pop();
		if(n instanceof PsiInteger && j instanceof PsiInteger)
		{
			int nValue=((PsiInteger)n).getValue().intValue();
			int jValue=((PsiInteger)j).getValue().intValue();
			int opstackSize=opstack.size();
			if(nValue<0)
			{
				interpreter.error("rangecheck");
				return;
			}
			else if(opstackSize<nValue)
			{
				interpreter.error("stackunderflow");
				return;
			}
			else
			{
				while(jValue<0)
					jValue+=nValue;
				jValue%=nValue;
				for(int i=0; i<jValue; i++)
					opstack.add(opstackSize-nValue, opstack.pop());
			}
		}
		else
			interpreter.error("typecheck");
	}
}
