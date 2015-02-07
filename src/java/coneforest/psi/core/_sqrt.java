package coneforest.psi.core;
import coneforest.psi.*;

public class _sqrt extends PsiOperator
{
	@Override
	public void invoke(final Interpreter interpreter)
	{
		final OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.handleError("stackunderflow", this);
			return;
		}

		final PsiObject cn=opstack.pop();
		try
		{
			opstack.push(((PsiComplexNumeric)cn).psiSqrt());
		}
		catch(ClassCastException e)
		{
			opstack.push(cn);
			interpreter.handleError(e, this);
		}

		/*
		if(obj instanceof PsiNumeric)
		{
			PsiReal result=PsiNumeric.sqrt((PsiNumeric)obj);
			if(result.getValue().isNaN())
				interpreter.handleError("rangecheck");
			else
				opstack.push(result);
		}
		else
			interpreter.handleError("typecheck");
		*/
	}
}
