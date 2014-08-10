package coneforest.psi.core;
import coneforest.psi.*;

public class _and extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject logical2=opstack.pop();
		PsiObject logical1=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiLogical)logical1).psiAnd((PsiLogical)logical2));
		}
		catch(Exception e)
		{
			opstack.push(logical1);
			opstack.push(logical2);
			interpreter.error(e, this);
		}
	}
}
