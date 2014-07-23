package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _exp extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<0)
		{
			interpreter.error("stackunderflow");
			return;
		}
		
		PsiObject numeric=opstack.pop();

		try
		{
			opstack.push(((PsiNumeric)numeric).exp());
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
		/*
		if(obj instanceof PsiNumeric)
			opstack.push(PsiNumeric.exp((PsiNumeric)obj));
		else
		{
			opstack.push(obj);
			interpreter.error("typecheck");
		}
		*/
	}
}
