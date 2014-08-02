package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _roll extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();

		if(opstack.size()<3)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject j=opstack.pop();
		PsiObject n=opstack.pop();
		try
		{
			int nValue=((PsiInteger)n).getValue().intValue();
			int jValue=((PsiInteger)j).getValue().intValue();
			int opstackSize=opstack.size();
			if(nValue<0)
				throw new PsiException("rangecheck");
			else if(opstackSize<nValue)
				throw new PsiException("stackunderflow");
			else
			{
				while(jValue<0)
					jValue+=nValue;
				jValue%=nValue;
				for(int i=0; i<jValue; i++)
					opstack.add(opstackSize-nValue, opstack.pop());
			}
		}
		catch(ClassCastException e)
		{
			opstack.push(n);
			opstack.push(j);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(n);
			opstack.push(j);
			interpreter.error(e.kind(), this);
		}
	}
}
