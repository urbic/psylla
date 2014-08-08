package coneforest.psi.core;
import coneforest.psi.*;

public class _log extends PsiOperator
{
	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow", this);
			return;
		}

		PsiObject cn=opstack.pop();
		try
		{
			opstack.push(((PsiComplexNumeric)cn).psiLog());
		}
		catch(ClassCastException e)
		{
			opstack.push(cn);
			interpreter.error("typecheck", this);
		}
		catch(PsiException e)
		{
			opstack.push(cn);
			interpreter.error(e.kind(), this);
		}

		/*
		if(obj instanceof PsiNumeric)
		{
			PsiReal result=PsiNumeric.sqrt((PsiNumeric)obj);
			if(result.getValue().isNaN())
				interpreter.error("rangecheck");
			else
				opstack.push(result);
		}
		else
			interpreter.error("typecheck");
		*/
	}
}
