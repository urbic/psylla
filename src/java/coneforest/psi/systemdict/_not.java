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
		PsiObject n=opstack.pop();

		if(n instanceof PsiBoolean)
			opstack.push(PsiBoolean.not((PsiBoolean)n));
		else if(n instanceof PsiInteger)
			opstack.push(PsiInteger.not((PsiInteger)n));
		else
		{
			opstack.push(n);
			interpreter.error("typecheck");
		}
	}
}