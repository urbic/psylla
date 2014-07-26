package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _arg extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		
		PsiObject cn=opstack.pop();

		try
		{
			opstack.push(((PsiComplexNumeric)cn).arg());
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
		/*
		if(obj instanceof PsiNumeric)
			opstack.push(PsiNumeric.abs((PsiNumeric)obj));
		else
		{
			opstack.push(obj);
			interpreter.error("typecheck");
		}
		*/
	}
}
