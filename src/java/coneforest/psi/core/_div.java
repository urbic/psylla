package coneforest.psi.core;
import coneforest.psi.*;

public class _div extends PsiOperator
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

		PsiObject arithmetic2=opstack.pop();
		PsiObject arithmetic1=opstack.pop();
		try
		{
			opstack.push((PsiObject)((PsiArithmetic)arithmetic1).psiDiv((PsiArithmetic)arithmetic2));
		}
		catch(Exception e)
		{
			opstack.push(arithmetic1);
			opstack.push(arithmetic2);
			interpreter.error(e, this);
		}
	}
}
