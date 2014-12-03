package coneforest.psi.core;
import coneforest.psi.*;

public class _floor extends PsiOperator
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

		PsiObject numeric=opstack.pop();
		try
		{
			opstack.push(((PsiNumeric)numeric).psiFloor());
		}
		catch(ClassCastException e)
		{
			opstack.push(numeric);
			interpreter.error(e, this);
		}
	}
}
