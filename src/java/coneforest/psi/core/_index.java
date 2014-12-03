package coneforest.psi.core;
import coneforest.psi.*;

public class _index extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject n=opstack.pop();
		try
		{
			int nValue=((PsiInteger)n).intValue();
			if(nValue<0)
				throw new PsiException("rangecheck");
			else if(opstack.size()<nValue+1)
				throw new PsiException("stackunderflow");
			else
				opstack.push(opstack.get(opstack.size()-nValue-1));
		}
		catch(ClassCastException|PsiException e)
		{
			opstack.push(n);
			interpreter.error(e, this);
		}
	}
}
