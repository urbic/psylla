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
		PsiObject n2=opstack.pop();
		PsiObject n1=opstack.pop();

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
	}
}
