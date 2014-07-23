package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _not extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<1)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject logical=opstack.pop();

		try
		{
			opstack.push((PsiObject)((PsiLogical)logical).not());
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
		/*
		if(n instanceof PsiLogical)
		{
			opstack.push((PsiObject)((PsiLogical)logical).not());
		}
		//if(n instanceof PsiBoolean)
		//	opstack.push(PsiBoolean.not((PsiBoolean)n));
		//else if(n instanceof PsiInteger)
		//	opstack.push(PsiInteger.not((PsiInteger)n));
		else
		{
			opstack.push(n);
			interpreter.error("typecheck");
		}
		*/
	}
}
