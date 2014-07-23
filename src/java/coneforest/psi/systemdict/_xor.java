package coneforest.psi.systemdict;
import coneforest.psi.*;

public class _xor extends PsiOperator
{
	public void execute(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(opstack.size()<2)
		{
			interpreter.error("stackunderflow");
			return;
		}
		PsiObject logical2=opstack.pop();
		PsiObject logical1=opstack.pop();

		try
		{
			opstack.push((PsiObject)((PsiLogical)logical2).xor((PsiLogical)logical1));
		}
		catch(ClassCastException e)
		{
			interpreter.error("typecheck");
		}
		/*
		if(n1 instanceof PsiBoolean && n2 instanceof PsiBoolean)
			opstack.push(PsiBoolean.xor((PsiBoolean)n1, (PsiBoolean)n2));
		else if(n1 instanceof PsiInteger && n2 instanceof PsiInteger)
			opstack.push(PsiInteger.xor((PsiInteger)n1, (PsiInteger)n2));
		else
		{
			opstack.push(n1);
			opstack.push(n2);
			interpreter.error("typecheck");
		}
		*/
	}
}
